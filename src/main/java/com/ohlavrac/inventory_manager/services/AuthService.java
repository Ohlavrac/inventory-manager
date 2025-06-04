package com.ohlavrac.inventory_manager.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.infra.security.SecurityConfig;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public AuthService(
        UserRepository userRepository,
        SecurityConfig securityConfig
    ) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public void RegisterUser(CreateUserDTO data) {

        Optional<UserEntity> user = userRepository.findByEmail(data.email());

        if (user.isPresent()) {
            throw new AuthException("A Account With This Email Aready Exists.");
        }

        String encodedPassword = securityConfig.passwordEncoder().encode(data.password()); 

        UserEntity newUser = new UserEntity();
        newUser.setEmail(data.email());
        newUser.setPassword(encodedPassword);
        newUser.setUserName(data.username());
        newUser.setUserRole(data.role());

        userRepository.save(newUser);
    }
}
