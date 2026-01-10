package com.e_com.e_com_spring.service.auth.checker;

public interface IAuthChecker {
    void verifyEmail(String email);
    void validateRole(String role);
}
