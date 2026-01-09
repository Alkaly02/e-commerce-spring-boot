package com.e_com.e_com_spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public void signup(){
        System.out.println("User registered");
    }

    @GetMapping("me")
    public String me(){
        return "Safoul Boukanome";
    }
}
