package com.ohlavrac.inventory_manager.dtos.users;

import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.enums.UserRoles;

public record UserResponseDTO(
    UUID id,
    String username,
    String email,
    UserRoles roles
) {
    
}
