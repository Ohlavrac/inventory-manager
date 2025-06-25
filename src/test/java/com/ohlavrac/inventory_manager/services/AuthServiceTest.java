package com.ohlavrac.inventory_manager.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.UserRoles;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
import com.ohlavrac.inventory_manager.infra.security.SecurityConfig;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SecurityConfig securityConfig;
    
    @Mock
    private TokenService tokenService;
    
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should create new user when email does not exist")
    void testRegisterUserCase1() {
        CreateUserDTO data = new CreateUserDTO(
            "fakeemail@gmail.com",
            "contafake",
            "12345678",
            UserRoles.EMPLOYER
        );

        when(userRepository.findByEmail(data.email())).thenReturn(Optional.empty());
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(data.password())).thenReturn("crypted-password");

        authService.registerUser(data);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        

        UserEntity userSaved = captor.getValue();
        assertEquals(data.email(), userSaved.getEmail());
        assertEquals(data.username(), userSaved.getUserName());
        assertEquals("crypted-password", userSaved.getPassword());
        assertEquals(data.role(), userSaved.getUserRole());
    }

    @Test
    void testRegisterUserCase2() {

    }
}
