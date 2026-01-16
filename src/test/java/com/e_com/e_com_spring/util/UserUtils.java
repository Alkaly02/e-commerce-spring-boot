package com.e_com.e_com_spring.util;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;

public class UserUtils {

    public static RegisterPostDto createRegisterPostDto(String firstName, String lastName, String email, String password, String roleType){
        RegisterPostDto registerPostDto = new RegisterPostDto();
        registerPostDto.setFirstName(firstName);
        registerPostDto.setLastName(lastName);
        registerPostDto.setEmail(email);
        registerPostDto.setPassword(password);
        registerPostDto.setRoleType(roleType);
        return registerPostDto;
    }
}
