package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.dtos.users.UserResponseDTO;

public class UserMapper {
    public UserResponseDTO entityToResponse(UserEntity entity) {
        return new UserResponseDTO(
            entity.getId(), 
            entity.getUserName(), 
            entity.getEmail(), 
            entity.getUserRole()
        );
    }
}
