package com.e_com.e_com_spring.service.auth.createUser;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.RoleRepository;
import com.e_com.e_com_spring.repository.UserRepository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private CreateUserService createUserService;

    @Nested
    class CreateTests{
        @Test
        void shouldCreateUser(){
            // Given
            RegisterPostDto registerPostDto = new RegisterPostDto();
            registerPostDto.setFirstName("Mocked firstName");
            registerPostDto.setLastName("Mocked lastName");
            registerPostDto.setEmail("mocked@gmail.com");
            registerPostDto.setPassword("passer123");
            registerPostDto.setRoleType("ROLE_ADMIN");

            Role role = new Role();
            role.setId(1L);
            role.setName("Admin");
            role.setRoleType(RoleType.ROLE_ADMIN);

            User convertedUser = new User();
            convertedUser.setFirstName("Mocked firstName");
            convertedUser.setLastName("Mocked lastName");
            convertedUser.setEmail("mocked@gmail.com");
            convertedUser.setPassword("passer123");
            convertedUser.setRole(role);

            User savedUser = new User();
            savedUser.setId(1L);
            savedUser.setFirstName("Mocked firstName");
            savedUser.setLastName("Mocked lastName");
            savedUser.setEmail("mocked@gmail.com");
            savedUser.setPassword("passer123");
            savedUser.setPassword("hashed_password");

            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

            when(userMapper.registerDtoToUser(registerPostDto)).thenReturn(convertedUser);
            when(roleRepository.findByRoleType(any(RoleType.class))).thenReturn(Optional.of(role));
            when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
            when(userRepository.save(any(User.class))).thenReturn(savedUser);

            // When
            User actual = createUserService.create(registerPostDto);

            // Then
            assertNotNull(actual);
            assertEquals(actual.getId(), 1L);
            assertEquals(actual.getPassword(), "hashed_password");
            assertEquals(actual.getEmail(), registerPostDto.getEmail());

            verify(userMapper, times(1)).registerDtoToUser(registerPostDto);
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());

            User capturedUser = userArgumentCaptor.getValue();

            assertNotNull(capturedUser);
            assertEquals(capturedUser.getFirstName(), registerPostDto.getFirstName());
            assertEquals(capturedUser.getRole().getRoleType().name(), registerPostDto.getRoleType());
            assertEquals(capturedUser.getPassword(), "hashed_password");
        }
    }
}