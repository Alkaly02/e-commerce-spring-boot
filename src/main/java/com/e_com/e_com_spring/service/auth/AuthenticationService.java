package com.e_com.e_com_spring.service.auth;

import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.dto.auth.LoginResponseDto;
import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.dto.auth.RegisterResponseDto;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.service.auth.checker.IAuthChecker;
import com.e_com.e_com_spring.service.auth.createUser.ICreateUserService;
import com.e_com.e_com_spring.service.jwt.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    private final IAuthChecker authChecker;
    private final ICreateUserService createUserService;
    private final UserMapper userMapper;
    private final IJwtService jwtService;

    @Override
    public RegisterResponseDto register(RegisterPostDto postDto) {
        authChecker.ensureEmailNotExists(postDto.getEmail());
        authChecker.validateRole(postDto.getRoleType());
        createUserService.create(postDto);
        // TODO: get saved used and send email confirmation. Remove newUser.setEnabled(true) inside create(postDto)
        return new RegisterResponseDto("User successfully registered");
    }

    @Override
    public LoginResponseDto login(LoginPostDto postDto) {
        User user = authChecker.ensureEmailExists(postDto.getEmail());
        authChecker.verifyIfUserEnabled(user);
        authChecker.verifyPassword(postDto, user);
        return new LoginResponseDto(jwtService.generateToken(user.getEmail()), userMapper.toUserMiniDto(user));
    }
}
