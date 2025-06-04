package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
import com.ohlavrac.inventory_manager.dtos.users.JWTTokenResponseDTO;
import com.ohlavrac.inventory_manager.dtos.users.LoginUserDTO;
import com.ohlavrac.inventory_manager.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody CreateUserDTO data) {
        authService.RegisterUser(data);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO data) {
        JWTTokenResponseDTO token = authService.authUser(data);
        
        return ResponseEntity.ok().body(token);
    }
    
}
