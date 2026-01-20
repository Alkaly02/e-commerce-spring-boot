package com.e_com.e_com_spring.service.admin.user.checker;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckerTest {

    @InjectMocks
    private UserChecker userChecker;

    private User user;
    private Role role;

    @BeforeEach
    void setup(){
        user = new User();
        role = new Role();
        user.setRole(role);
    }

    @AfterEach
    void tearDown(){
        user = null;
        role = null;
    }

    @Test
    void shouldThrowException_WhenWhenUserNotAdmin(){
        role.setRoleType(RoleType.ROLE_CUSTOMER);
        // When
        CustomException exception = assertThrows(CustomException.class, () -> userChecker.canPerformAdminAction(user));
        // Then
        assertEquals(exception.getMessage(), "You are not allowed to perform this action");
        assertEquals(exception.getStatus(), HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotThrowException_WhenWhenUserIsAdmin(){
        role.setRoleType(RoleType.ROLE_ADMIN);
        // When
        assertDoesNotThrow(() -> userChecker.canPerformAdminAction(user));
    }
}