package com.e_com.e_com_spring.service.auth.checker;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthChecker implements IAuthChecker {
    private final UserRepository userRepository;

    @Override
    public void verifyEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            throw new CustomException("User with this email already exists", HttpStatus.CONFLICT);
        }
    }

    @Override
    public void validateRole(String role) {
        boolean roleExists = Arrays.stream(RoleType.values())
                .anyMatch(roleType -> roleType.name().equals(role.toUpperCase()));
        if (!roleExists){
            String joinedRoles = Arrays.stream(RoleType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining());
            throw new CustomException("role must be one of this: " + joinedRoles, HttpStatus.BAD_REQUEST);
        }
    }

}
