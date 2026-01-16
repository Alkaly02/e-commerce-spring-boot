package com.e_com.e_com_spring.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PutMapping("{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Long id){
        return null;
    }

    @PutMapping("{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Long id){
        return null;
    }
}
