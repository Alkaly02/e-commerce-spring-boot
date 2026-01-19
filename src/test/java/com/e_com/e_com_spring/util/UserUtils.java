package com.e_com.e_com_spring.util;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.User;

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

    public static User createUser(Long id, String firstName, String lastName, String email, String password, Role role){
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
}
