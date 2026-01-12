package com.e_com.e_com_spring.service.auth;

import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.dto.auth.LoginResponseDto;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.auth.RegisterResponseDto;

public interface IAuthenticationService {
    RegisterResponseDto register(RegisterPostDto postDto);

    LoginResponseDto login(LoginPostDto postDto);
}
