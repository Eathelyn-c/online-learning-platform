package com.spm5.olp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKeyString;
    
    private static SecretKey secretKey;
    private static long expiration = 86400000;

    @javax.annotation.PostConstruct
    public void init() {
        // 初始化密钥
        secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    /**
     * 生成JWT token
     */
    public static String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, userId);
        return token;
    }

    /**
     * 验证JWT token
     */
    public static Boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中提取用户ID
     */
    public static String getUserIdFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 检查token是否过期
     */
    private static Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 从token中获取过期时间
     */
    private static Date getExpirationDateFromToken(String token) {
        try {
            return getClaimsFromToken(token).getExpiration();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 从token中获取claims
     */
    private static Claims getClaimsFromToken(String token) {
        // 移除可能的Bearer前缀
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }
        
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 创建token
     */
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }
}