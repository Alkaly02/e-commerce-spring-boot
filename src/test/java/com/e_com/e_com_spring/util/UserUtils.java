package com.e_com.e_com_spring.util;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.service.auth.IAuthenticationService;
import com.e_com.e_com_spring.service.jwt.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final IAuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final IJwtService jwtService;

    public RegisterPostDto createRegisterPostDto(String firstName, String lastName, String email, String password, String roleType){
        RegisterPostDto registerPostDto = new RegisterPostDto();
        registerPostDto.setFirstName(firstName);
        registerPostDto.setLastName(lastName);
        registerPostDto.setEmail(email);
        registerPostDto.setPassword(password);
        registerPostDto.setRoleType(roleType);
        return registerPostDto;
    }

    public User createUser(Long id, String firstName, String lastName, String email, String password, Role role){
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }

    public User registerUser(RegisterPostDto postDto){
        authenticationService.register(postDto);
        userRepository.flush();
        return userRepository.findByEmail(postDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after registration: " + postDto.getEmail()));
    }

    private String getToken(String email){
        return jwtService.generateToken(email);
    }

    public HttpHeaders getHttpHeaders(String email){
        String token = getToken(email);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
