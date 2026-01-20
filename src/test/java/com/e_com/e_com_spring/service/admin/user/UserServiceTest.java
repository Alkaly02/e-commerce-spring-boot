package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import static com.e_com.e_com_spring.util.UserUtils.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User currentUser;

    @BeforeEach
    void setup(){
        Role role = new Role();
        role.setRoleType(RoleType.ROLE_ADMIN);
        currentUser = createUser(1L, "Lka", "Dev", "lka@gmail.com", null, role);
    }

    @AfterEach
    void tearDown(){
        currentUser = null;
    }

    @Nested
    class EnableUserTests{
        // Should get User
        // Verify if user exists, else throw exception
        // Verify if current User has privileges to update the user
        // update user
        @Test
        void shouldEnableUser(){
            // TODO: Create admin user
            // Given
            Long userId = 1L;
            User user = createUser(userId,"Lka", "Dev", "lka@gmail.com", "mockedPassword", null);
            UserMiniDto userMiniDto = UserMiniDto.builder()
                    .firstName("Lka")
                    .lastName("Dev")
                    .enabled(true)
                    .build();
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(userRepository.save(any())).thenReturn(user);
            when(userMapper.toUserMiniDto(any(User.class))).thenReturn(userMiniDto);
            // When
            UserMiniDto actual = userService.enable(userId, currentUser);
            // Then
            assertNotNull(actual);
            assertTrue(actual.isEnabled());
        }

        @Test
        void shouldThrowException_WhenUserDoesNotExist(){
            // Given
            Long idNotExists = 9999L;
            when(userRepository.findById(idNotExists)).thenReturn(Optional.empty());
            // When
            CustomException exception = assertThrows(CustomException.class, () -> userService.enable(idNotExists, currentUser));
            // Then
            assertEquals(exception.getMessage(), "User does not exist");
            assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
        }

        @Test
        @Disabled
        void shouldThrowException_WhenUserDoesNotHave_EDIT_USERS_Privilege(){
            // Given
            Role role = new Role();
            role.setRoleType(RoleType.ROLE_CUSTOMER);
            currentUser.setRole(role);
            Long userId = 1L;
            User user = createUser(userId,"Lka", "Dev", "lka@gmail.com", "mockedPassword", null);
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            // When
            CustomException exception = assertThrows(CustomException.class, () -> userService.enable(userId, currentUser));
            // Then
            assertEquals(exception.getMessage(), "You are not allowed to perform this action");
            assertEquals(exception.getStatus(), HttpStatus.FORBIDDEN);
        }
    }

}