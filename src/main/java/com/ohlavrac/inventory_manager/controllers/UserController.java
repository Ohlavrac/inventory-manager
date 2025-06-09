package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.users.UserResponseDTO;
import com.ohlavrac.inventory_manager.services.UsersService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UsersService usersService;

    public UserController (
        UsersService usersService
    ) {
        this.usersService = usersService;
    }

    //TODO CHANGE THE ENDPOINT
    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestHeader(value = "Authorization") String token) {

        UserResponseDTO response = usersService.getUserDataByToken(token);
        return ResponseEntity.ok().body(response);
    }
    
}
