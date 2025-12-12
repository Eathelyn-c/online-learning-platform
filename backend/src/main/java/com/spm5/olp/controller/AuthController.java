package com.spm5.olp.controller;

import com.spm5.olp.dto.*;
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
    
    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            System.out.println("Logout request received with token: " + token);
            
            // 在实际项目中，这里可以将token加入黑名单或缓存中，
            // 以便在token过期前使其失效
            // 出于简化目的，我们只是记录日志
            
            if (token != null && !token.isEmpty()) {
                String cleanToken = token.replace("Bearer ", "");
                System.out.println("User logged out with token: " + cleanToken);
                // 实际项目中可以在这里添加token到黑名单的逻辑
            }
            
            return ResponseEntity.ok("退出登录成功");
        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("退出登录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/user/info")
    public ResponseEntity<?> updateUserInfo(
            @RequestHeader(value = "Authorization", required = false) String token,
            @Valid @RequestBody UpdateUserInfoRequestDto updateUserInfoRequest) {
        try {
            System.out.println("Update user info request received with token: " + token);
            
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
            
            // 更新用户信息
            boolean result = authService.updateUserInfo(userId, updateUserInfoRequest);
            
            if (result) {
                return ResponseEntity.ok("更新成功");
            } else {
                return ResponseEntity.badRequest().body("更新失败");
            }
        } catch (Exception e) {
            System.err.println("Error updating user info: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("更新失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改密码
     */
    @PutMapping("/user/password")
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "Authorization", required = false) String token,
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequest) {
        try {
            System.out.println("Change password request received with token: " + token);
            
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
            
            // 修改密码
            boolean result = authService.changePassword(userId, changePasswordRequest);
            
            if (result) {
                return ResponseEntity.ok("修改成功");
            } else {
                return ResponseEntity.badRequest().body("修改失败，原密码错误");
            }
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("修改失败: " + e.getMessage());
        }
    }
}