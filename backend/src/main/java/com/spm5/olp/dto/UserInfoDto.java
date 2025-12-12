package com.spm5.olp.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    private String phone;
    private Integer gender;
    private LocalDateTime birthday;
    private List<String> roles;
}