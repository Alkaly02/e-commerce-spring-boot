package com.e_com.e_com_spring.service.auth.checker;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.Role;
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

    private User authUser;
    String mockedEmailToFind;

    @BeforeEach
    void setUp() {
        authUser = createUser(1L, "Mocked firstName", "Mocked lastName", "mocked@gmail.com", null);
        mockedEmailToFind = "mocked@gmail.com";
    }

    @AfterEach
    void tearDown() {
        authUser = null;
        mockedEmailToFind = null;
    }

    @Nested
    class EnsureEmailNotExistsTests{
        @Test
        void shouldThrowException_WhenEmailExists(){
            // Given
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.of(authUser));
            // When
            CustomException exception = assertThrows(
                    CustomException.class,
                    () -> authChecker.ensureEmailNotExists(mockedEmailToFind)
            );
            // Then
            assertEquals(exception.getMessage(), "User with this email already exists");
            assertEquals(exception.getStatus(), HttpStatus.CONFLICT);
            verify(userRepository, times(1)).findByEmail(mockedEmailToFind);
        }

        @Test
        void shouldNotThrowException_WhenEmailExists(){
            // Given
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.empty());
            // When
            assertDoesNotThrow(() -> authChecker.ensureEmailNotExists(mockedEmailToFind));
            verify(userRepository, times(1)).findByEmail(mockedEmailToFind);
        }
    }

    @Nested
    class EnsureEmailExistsTests{
        @Test
        void shouldReturnUser_WhenEmailExists(){
            // Given
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.of(authUser));
            // When
            User actual = authChecker.ensureEmailExists(mockedEmailToFind);
            // Then
            assertNotNull(actual);
            assertEquals(actual, authUser);
            verify(userRepository, times(1)).findByEmail(mockedEmailToFind);
        }
        @Test
        void shouldThrowException_WhenEmailDoesNotExist(){
            // Given
            when(userRepository.findByEmail(mockedEmailToFind)).thenReturn(Optional.empty());
            // When
            CustomException exception = assertThrows(
                    CustomException.class,
                    () -> authChecker.ensureEmailExists(mockedEmailToFind)
            );
            // Then
            assertEquals(exception.getMessage(), "email or password incorrect");
            assertEquals(exception.getStatus(), HttpStatus.UNAUTHORIZED);
            verify(userRepository, times(1)).findByEmail(mockedEmailToFind);
        }
    }

    @Nested
    class VerifyIfUserEnabledTests{
        @Test
        void shouldThrowException_WhenUserNotEnabled(){
            // Given
            User notEnabledUser = mock(User.class);
            notEnabledUser.setEnabled(false);
            when(notEnabledUser.isEnabled()).thenReturn(false);
            // When
            CustomException exception = assertThrows(
                    CustomException.class, () -> authChecker.verifyIfUserEnabled(notEnabledUser)
            );
            // Then
            assertEquals(exception.getMessage(), "Your account is not enabled");
            assertEquals(exception.getStatus(), HttpStatus.FORBIDDEN);
        }

        @Test
        void shouldNotThrowException_WhenUserIsEnabled(){
            // Given
            User notEnabledUser = mock(User.class);
            notEnabledUser.setEnabled(true);
            when(notEnabledUser.isEnabled()).thenReturn(true);
            // When
            assertDoesNotThrow(() -> authChecker.verifyIfUserEnabled(notEnabledUser));
        }
    }


    private User createUser(Long id, String firstName, String lastName, String email, Role role){
        User mockedUser = new User();
        mockedUser.setId(id);
        mockedUser.setFirstName(firstName);
        mockedUser.setLastName(lastName);
        mockedUser.setEmail(lastName);
        mockedUser.setRole(role);
        mockedUser.setEnabled(true);
        return mockedUser;
    }
}