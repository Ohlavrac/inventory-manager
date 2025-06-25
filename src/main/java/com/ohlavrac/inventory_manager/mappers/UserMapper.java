package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
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

    public UserEntity toEntity(CreateUserDTO dto) {
        UserEntity entity = new UserEntity();

        entity.setEmail(dto.email());
        entity.setUserName(dto.username());
        entity.setPassword(dto.password());
        entity.setUserRole(dto.role());

        return entity;
    }
}
