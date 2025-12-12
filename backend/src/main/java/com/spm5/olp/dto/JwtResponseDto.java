package com.spm5.olp.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    
    public JwtResponseDto(String token, Long id, String username, String email, String nickname, String avatar) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.avatar = avatar;
    }
}