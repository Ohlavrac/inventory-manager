package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.domain.enums.UserRoles;
import com.ohlavrac.inventory_manager.dtos.users.RoleRequestDTO;
import com.ohlavrac.inventory_manager.dtos.users.UserResponseDTO;
import com.ohlavrac.inventory_manager.services.AdminService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(
        AdminService adminService
    ) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "Authorization") String token) {
        List<UserResponseDTO> response = adminService.getUsers(token);
        return ResponseEntity.ok().body(response); 
    }
    

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@RequestHeader(value = "Authorization") String token, @PathVariable UUID id, @RequestBody RoleRequestDTO newRole) {

        UserResponseDTO response = adminService.updateUserRole(token, id, newRole.role());

        return ResponseEntity.ok().body(response);
    }
}
