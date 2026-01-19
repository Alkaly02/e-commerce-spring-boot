package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import static com.e_com.e_com_spring.util.UserUtils.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @InjectMocks
    private UserService userService;

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
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            // When
            UserMiniDto actual = userService.enable(userId);
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
            CustomException exception = assertThrows(CustomException.class, () -> userService.enable(idNotExists));
            // Then
            assertEquals(exception.getMessage(), "User does not exist");
            assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
        }

        @Test
        @Disabled
        void shouldThrowException_WhenUserDoesNotHave_EDIT_USERS_Privilege(){
            // Given

        }
    }

}