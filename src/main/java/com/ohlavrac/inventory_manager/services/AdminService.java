package com.ohlavrac.inventory_manager.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.UserRoles;
import com.ohlavrac.inventory_manager.dtos.users.UserResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.mappers.UserMapper;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public AdminService(
        UserRepository userRepository,
        TokenService tokenService,
        UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getUsers(String token) {
        getAdminDataWithToken(token);

        List<UserEntity> users = userRepository.findAll();

        return users.stream().map((user) -> userMapper.entityToResponse(user)).toList();
    }

    public UserResponseDTO updateUserRole(String token, UUID userID, UserRoles newRole) {
        getAdminDataWithToken(token);

        UserEntity userToUpdate = userRepository.findById(userID).orElseThrow(() -> new ResorceNotFoundException("User Not Found With ID: "+ userID));

        userRepository.updateUserRole(newRole, userToUpdate.getId());

        return new UserResponseDTO(
            userToUpdate.getId(),
            userToUpdate.getUserName(),
            userToUpdate.getEmail(),
            newRole
        );
    }

    public UserEntity getAdminDataWithToken(String token) {
        String email = tokenService.getSubjectFromToken(token);

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("Authentication Error"));

        if (userEntity.getUserRole() != UserRoles.ADMIN) {
            throw new AuthException("Role Authentication Error");
        }

        return userEntity;
    }
}
