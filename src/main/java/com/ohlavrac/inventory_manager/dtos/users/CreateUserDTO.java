package com.ohlavrac.inventory_manager.dtos.users;

import com.ohlavrac.inventory_manager.domain.enums.UserRoles;

public record CreateUserDTO(
    String email,
    String username,
    String password,
    UserRoles role
) {
}
