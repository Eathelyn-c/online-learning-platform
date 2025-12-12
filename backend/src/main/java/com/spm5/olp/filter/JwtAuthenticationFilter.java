package com.spm5.olp.filter;

import com.spm5.olp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        System.out.println("Processing request: " + request.getMethod() + " " + request.getRequestURI());
        
        try {
            String jwt = parseJwt(request);
            System.out.println("Parsed JWT: " + jwt);
            
            if (jwt != null) {
                System.out.println("Validating token...");
                if (JwtUtil.validateToken(jwt)) {
                    System.out.println("Token validated successfully");
                    String userId = JwtUtil.getUserIdFromToken(jwt);
                    System.out.println("User ID from token: " + userId);
                    
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Token validation failed");
                }
            } else {
                System.out.println("No JWT token found in request");
            }
        } catch (Exception e) {
            System.err.println("Cannot set user authentication: " + e.getMessage());
            e.printStackTrace();
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        System.out.println("Authorization header: " + headerAuth);
        
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7);
            System.out.println("Raw token from header: " + token);
            return token;
        }
        
        return null;
    }
}