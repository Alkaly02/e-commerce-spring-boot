package com.e_com.e_com_spring.service.auth.createUser;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.RoleRepository;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateUserService implements ICreateUserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User create(RegisterPostDto postDto) {
        User newUser = userMapper.registerDtoToUser(postDto);
        Optional<Role> role = roleRepository.findByRoleType(newUser.getRole().getRoleType());
        if (role.isEmpty()){
            throw new CustomException(postDto.getRoleType() + " not defined", HttpStatus.BAD_REQUEST);
        }
        newUser.setRole(role.get());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(true);
        return userRepository.save(newUser);
    }
}
