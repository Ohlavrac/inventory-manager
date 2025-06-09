package com.ohlavrac.inventory_manager.services;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.dtos.users.UserResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.mappers.UserMapper;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public UsersService(
        UserRepository userRepository,
        TokenService tokenService,
        UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public UserResponseDTO getUserDataByToken(String token) {
        String email = tokenService.getSubjectFromToken(token);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("User Not Authenticated"));

        UserResponseDTO response = userMapper.entityToResponse(user);
        return response;
    }
}
