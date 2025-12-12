package com.spm5.olp.controller;

import com.spm5.olp.dto.JwtResponseDto;
import com.spm5.olp.dto.LoginRequestDto;
import com.spm5.olp.dto.RegisterRequestDto;
import com.spm5.olp.dto.UserInfoDto;
import com.spm5.olp.service.AuthService;
import com.spm5.olp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 允许跨域请求
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        System.out.println("Login request received for user: " + loginRequest.getUsername());
        JwtResponseDto jwtResponse = authService.authenticateUser(loginRequest);
        System.out.println("Authentication result: " + (jwtResponse != null ? "SUCCESS" : "FAILED"));
        
        if (jwtResponse != null) {
            System.out.println("Token issued: " + jwtResponse.getToken());
            return ResponseEntity.ok(jwtResponse);
        } else {
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        System.out.println("Register request received for user: " + registerRequest.getUsername());
        boolean result = authService.registerUser(registerRequest);
        System.out.println("Registration result: " + (result ? "SUCCESS" : "FAILED"));
        
        if (result) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.badRequest().body("注册失败，用户名或邮箱已存在，或者密码不匹配");
        }
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/userinfo")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            System.out.println("Received token for userinfo: " + token);
            if (token == null || token.isEmpty()) {
                System.err.println("No authorization token provided");
                return ResponseEntity.badRequest().body("未提供认证token");
            }
            
            // 从token中提取用户ID
            String cleanToken = token.replace("Bearer ", "");
            System.out.println("Clean token: " + cleanToken);
            String userIdStr = JwtUtil.getUserIdFromToken(cleanToken);
            Long userId = Long.parseLong(userIdStr);
            System.out.println("User ID: " + userId);
            
            UserInfoDto userInfo = authService.getCurrentUserInfo(userId);
            
            if (userInfo != null) {
                return ResponseEntity.ok(userInfo);
            } else {
                return ResponseEntity.badRequest().body("用户不存在");
            }
        } catch (Exception e) {
            System.err.println("Error getting current user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("token无效: " + e.getMessage());
        }
    }
}