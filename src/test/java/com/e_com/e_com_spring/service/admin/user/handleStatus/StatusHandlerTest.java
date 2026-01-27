package com.e_com.e_com_spring.service.admin.user.handleStatus;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.util.UserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusHandlerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StatusHandler statusHandler;

    @Autowired
    private UserUtils userUtils;

    @Test
    void shouldEnableUser(){
        // Given
        Long userId = 1L;
        User user = userUtils.createUser(userId,"Lka", "Dev", "lka@gmail.com", "mockedPassword", null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        // When
        User actual = statusHandler.handleStatus(userId, true);
        // Then
        assertNotNull(actual);
        assertTrue(actual.isEnabled());
    }

    @Test
    void shouldDisableUser(){
        // Given
        Long userId = 1L;
        User user = userUtils.createUser(userId,"Lka", "Dev", "lka@gmail.com", "mockedPassword", null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        // When
        User actual = statusHandler.handleStatus(userId, false);
        // Then
        assertNotNull(actual);
        assertFalse(actual.isEnabled());
    }

    @Test
    void shouldThrowException_WhenUserDoesNotExist(){
        // Given
        Long idNotExists = 9999L;
        when(userRepository.findById(idNotExists)).thenReturn(Optional.empty());
        // When
        CustomException exception = assertThrows(CustomException.class, () -> statusHandler.handleStatus(idNotExists, false));
        // Then
        assertEquals(exception.getMessage(), "User does not exist");
        assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
    }
}