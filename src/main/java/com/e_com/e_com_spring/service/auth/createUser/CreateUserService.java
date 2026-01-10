package com.e_com.e_com_spring.service.auth.createUser;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements ICreateUserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User create(RegisterPostDto postDto) {
        User newUser = userMapper.registerDtoToUser(postDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(true);
        return userRepository.save(newUser);
    }
}
