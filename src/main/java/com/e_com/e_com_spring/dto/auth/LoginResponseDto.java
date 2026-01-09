package com.e_com.e_com_spring.dto.auth;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;
    private UserMiniDto user;
}
