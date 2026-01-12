package com.e_com.e_com_spring.service.auth.checker;

import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.model.User;

public interface IAuthChecker {
    void ensureEmailNotExists(String email);

    User ensureEmailExists(String email);

    void verifyIfUserEnabled(User user);

    void verifyPassword(LoginPostDto postDto, User user);

    void validateRole(String role);
}
