package com.e_com.e_com_spring.service.auth.checker;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthCheckerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthChecker authChecker;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class EnsureEmailNotExistsTests{
        @Test
        void shouldThrowException_WhenEmailExists(){
            // Given
            User mockedUser = new User();
            mockedUser.setId(1L);
            mockedUser.setFirstName("Mocked firstName");
            mockedUser.setLastName("Mocked lastName");
            mockedUser.setEmail("mocked@gmail.com");
            String mockedEmailToFind = "mocked@gmail.com";
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.of(mockedUser));
            // When
            CustomException exception = assertThrows(
                    CustomException.class,
                    () -> authChecker.ensureEmailNotExists(mockedEmailToFind)
            );
            // Then
            assertEquals(exception.getMessage(), "User with this email already exists");
            assertEquals(exception.getStatus(), HttpStatus.CONFLICT);
        }

        @Test
        void shouldNotThrowException_WhenEmailExists(){
            // Given
            String mockedEmailToFind = "mocked@gmail.com";
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.empty());
            // When
            assertDoesNotThrow(() -> authChecker.ensureEmailNotExists(mockedEmailToFind));
        }
    }
}