package com.e_com.e_com_spring.service.auth.checker;

import com.e_com.e_com_spring.dto.auth.LoginPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthChecker implements IAuthChecker {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void ensureEmailNotExists(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            throw new CustomException("User with this email already exists", HttpStatus.CONFLICT);
        }
    }

    @Override
    public User ensureEmailExists(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new CustomException("email or password incorrect", HttpStatus.UNAUTHORIZED);
        }
        return optionalUser.get();
    }

    @Override
    public void verifyIfUserEnabled(User user) {
        if (!user.isEnabled()){
            throw new CustomException("Your account is not enabled", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void verifyPassword(LoginPostDto postDto, User user) {
        if (!passwordEncoder.matches(postDto.getPassword(), user.getPassword())){
            throw new CustomException("email or password incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void validateRole(String roleType) {
        boolean roleExists = Arrays.stream(RoleType.values())
                .anyMatch(roleType1 -> roleType1.name().equals(roleType.toUpperCase()));
        if (!roleExists){
            String joinedRoles = Arrays.stream(RoleType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new CustomException("roleType must be one of this: " + joinedRoles, HttpStatus.BAD_REQUEST);
        }
    }

}
