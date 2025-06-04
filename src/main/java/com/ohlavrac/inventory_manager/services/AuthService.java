package com.ohlavrac.inventory_manager.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.user.UserDetailsImpl;
import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
import com.ohlavrac.inventory_manager.dtos.users.JWTTokenResponseDTO;
import com.ohlavrac.inventory_manager.dtos.users.LoginUserDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.infra.security.SecurityConfig;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        UserRepository userRepository,
        SecurityConfig securityConfig,
        TokenService tokenService,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
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

    public JWTTokenResponseDTO authUser(LoginUserDTO data) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

        return new JWTTokenResponseDTO(tokenService.generateToken(userDetailsImpl)); 
    }
}
