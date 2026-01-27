package com.e_com.e_com_spring.controller.admin;

import com.e_com.e_com_spring.annotation.CurrentUser;
import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.service.admin.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @PutMapping("{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserMiniDto> disable(@PathVariable(name = "id") Long userId, @CurrentUser User currentUser){
        return ResponseEntity.ok(userService.disable(userId, currentUser));
    }

    @PutMapping("{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserMiniDto> enable(@PathVariable(name = "id") Long userId, @CurrentUser User currentUser){
        return ResponseEntity.ok(userService.enable(userId, currentUser));
    }
}
