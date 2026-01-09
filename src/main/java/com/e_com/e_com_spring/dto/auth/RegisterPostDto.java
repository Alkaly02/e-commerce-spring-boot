package com.e_com.e_com_spring.dto.auth;

import lombok.Data;

@Data
public class RegisterPostDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
