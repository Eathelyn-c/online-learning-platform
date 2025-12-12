package com.spm5.olp.service;

import com.spm5.olp.dto.*;

public interface AuthService {
    /**
     * 用户登录
     */
    JwtResponseDto authenticateUser(LoginRequestDto loginRequest);

    /**
     * 用户注册
     */
    boolean registerUser(RegisterRequestDto registerRequest);

    /**
     * 获取当前用户信息
     */
    UserInfoDto getCurrentUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    boolean updateUserInfo(Long userId, UpdateUserInfoRequestDto updateUserInfoRequest);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, ChangePasswordRequestDto changePasswordRequest);
}