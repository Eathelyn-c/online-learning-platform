package com.spm5.olp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.spm5.olp.constant.SystemConstant;
import com.spm5.olp.dto.*;
import com.spm5.olp.entity.User;
import com.spm5.olp.entity.UserRole;
import com.spm5.olp.mapper.UserMapper;
import com.spm5.olp.mapper.UserRoleMapper;
import com.spm5.olp.service.AuthService;
import com.spm5.olp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录
     * @param loginRequest 登录请求参数
     * @return 登录响应参数
     */
    @Override
    public JwtResponseDto authenticateUser(LoginRequestDto loginRequest) {System.out.println("Authenticating user: " + loginRequest.getUsername());
        
        // 根据用户名查找用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginRequest.getUsername())
                   .or()
                   .eq("email", loginRequest.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        
        System.out.println("User found: " + (user != null ? "YES" : "NO"));

        // 检查用户是否存在以及密码是否正确
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("User authenticated successfully");
            
            // 生成JWT token
            String token = JwtUtil.generateToken(user.getId().toString());
            System.out.println("Token generated: " + token);
            
            // 获取用户角色
            List<String> roles = getUserRoles(user.getId());
            System.out.println("User roles: " + roles);
            
            // 返回JWT响应
            JwtResponseDto response = new JwtResponseDto(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getAvatar()
            );
            
            System.out.println("JWT Response DTO created");
            return response;
        }
        
        System.out.println("Authentication failed for user: " + loginRequest.getUsername());
        return null;
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求参数
     * @return 是否注册成功
     */
    @Override
    public boolean registerUser(RegisterRequestDto registerRequest) {
        System.out.println("Registering user: " + registerRequest.getUsername());
        
        // 检查用户名是否已存在
        QueryWrapper<User> usernameQuery = new QueryWrapper<>();
        usernameQuery.eq("username", registerRequest.getUsername());
        if (userMapper.selectCount(usernameQuery) > 0) {
            System.out.println("Username already exists: " + registerRequest.getUsername());
            return false; // 用户名已存在
        }
        
        // 检查邮箱是否已存在
        QueryWrapper<User> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", registerRequest.getEmail());
        if (userMapper.selectCount(emailQuery) > 0) {
            System.out.println("Email already exists: " + registerRequest.getEmail());
            return false; // 邮箱已存在
        }
        
        // 检查两次输入的密码是否一致
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            System.out.println("Passwords do not match");
            return false; // 密码不一致
        }
        
        // 创建新用户
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setNickname(registerRequest.getUsername()); // 默认昵称为用户名
        newUser.setStatus(SystemConstant.USER_STATUS_ENABLED); // 默认启用状态
        
        System.out.println("Saving new user to database");
        // 插入用户
        int result = userMapper.insert(newUser);
        System.out.println("Database insert result: " + result);

        if (result > 0) {
            System.out.println("User registered successfully with ID: " + newUser.getId());
            // 为新用户分配默认角色（学生）
            UserRole userRole = new UserRole();
            userRole.setUserId(newUser.getId());
            
            QueryWrapper<com.spm5.olp.entity.Role> roleQuery = new QueryWrapper<>();
            roleQuery.eq("name", SystemConstant.ROLE_STUDENT);
            com.spm5.olp.entity.Role studentRole = roleMapper.selectOne(roleQuery);
            
            if (studentRole != null) {
                userRole.setRoleId(studentRole.getId());
                userRoleMapper.insert(userRole);
                System.out.println("Assigned student role to user");
            }
            
            return true;
        }
        
        System.out.println("Failed to register user");
        return false;
    }

    /**
     * 获取当前用户的信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfoDto getCurrentUserInfo(Long userId) {
        System.out.println("Getting current user info for user ID: " + userId);
        User user = userMapper.selectById(userId);
        if (user == null) {
            System.out.println("User not found for ID: " + userId);
            return null;
        }
        
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setNickname(user.getNickname());
        userInfoDto.setAvatar(user.getAvatar());
        userInfoDto.setPhone(user.getPhone());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setBirthday(user.getBirthday());
        userInfoDto.setRoles(getUserRoles(userId));
        
        System.out.println("User info retrieved successfully");
        return userInfoDto;
    }

    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 用户角色列表
     */
    private List<String> getUserRoles(Long userId) {
        System.out.println("Getting roles for user ID: " + userId);
        List<String> roles = new ArrayList<>();
        
        // 查询用户角色
        QueryWrapper<UserRole> userRoleQuery = new QueryWrapper<>();
        userRoleQuery.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQuery);
        System.out.println("Found " + userRoles.size() + " user roles");
        
        for (UserRole userRole : userRoles) {
            com.spm5.olp.entity.Role role = roleMapper.selectById(userRole.getRoleId());
            if (role != null) {
                roles.add(role.getName());
                System.out.println("Added role: " + role.getName());
            }
        }
        
        return roles;
    }

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param updateUserInfoRequest 更新用户信息请求参数
     * @return 是否更新成功
     */
    @Override
    public boolean updateUserInfo(Long userId, UpdateUserInfoRequestDto updateUserInfoRequest) {
        System.out.println("Updating user info for user ID: " + userId);
        
        // 查找用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            System.out.println("User not found for ID: " + userId);
            return false;
        }
        
        // 更新用户信息
        if (updateUserInfoRequest.getNickname() != null) {
            user.setNickname(updateUserInfoRequest.getNickname());
        }
        
        if (updateUserInfoRequest.getAvatar() != null) {
            user.setAvatar(updateUserInfoRequest.getAvatar());
        }
        
        if (updateUserInfoRequest.getPhone() != null) {
            user.setPhone(updateUserInfoRequest.getPhone());
        }
        
        if (updateUserInfoRequest.getGender() != null) {
            user.setGender(updateUserInfoRequest.getGender());
        }
        
        if (updateUserInfoRequest.getBirthday() != null) {
            try {
                user.setBirthday(java.time.LocalDateTime.parse(updateUserInfoRequest.getBirthday()));
            } catch (Exception e) {
                System.err.println("Error parsing birthday: " + e.getMessage());
            }
        }
        
        // 更新数据库
        int result = userMapper.updateById(user);
        System.out.println("User info update result: " + result);
        
        return result > 0;
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param changePasswordRequest 修改密码请求参数
     * @return 是否修改成功
     */
    @Override
    public boolean changePassword(Long userId, ChangePasswordRequestDto changePasswordRequest) {
        System.out.println("Changing password for user ID: " + userId);
        
        // 查找用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            System.out.println("User not found for ID: " + userId);
            return false;
        }
        
        // 检查原密码是否正确
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            System.out.println("Old password is incorrect");
            return false;
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        
        // 更新数据库
        int result = userMapper.updateById(user);
        System.out.println("Password change result: " + result);
        
        return result > 0;
    }
    
    // 注入RoleMapper
    @Autowired
    private com.spm5.olp.mapper.RoleMapper roleMapper;
}