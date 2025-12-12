package com.spm5.olp.dto;

import lombok.Data;

@Data
public class UpdateUserInfoRequestDto {
    private String nickname;
    private String avatar;
    private String phone;
    private Integer gender;
    private String birthday;
}