package com.e_com.e_com_spring.controller.auth;

import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.dto.auth.LoginResponseDto;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.auth.RegisterResponseDto;
import com.e_com.e_com_spring.service.auth.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(RegisterPostDto postDto) {
        return ResponseEntity.ok(authenticationService.register(postDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginPostDto postDto) {
        return null;
    }
}
