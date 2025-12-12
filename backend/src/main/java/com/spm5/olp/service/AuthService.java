package com.spm5.olp.service;

import com.spm5.olp.dto.JwtResponseDto;
import com.spm5.olp.dto.LoginRequestDto;
import com.spm5.olp.dto.RegisterRequestDto;
import com.spm5.olp.dto.UserInfoDto;

public interface AuthService {
    JwtResponseDto authenticateUser(LoginRequestDto loginRequest);
    boolean registerUser(RegisterRequestDto registerRequest);
    UserInfoDto getCurrentUserInfo(Long userId);
}