package com.ohlavrac.inventory_manager.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ohlavrac.inventory_manager.domain.entities.user.UserDetailsImpl;
import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.UserRoles;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;
import com.ohlavrac.inventory_manager.dtos.users.JWTTokenResponseDTO;
import com.ohlavrac.inventory_manager.dtos.users.LoginUserDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.infra.security.SecurityConfig;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.mappers.UserMapper;
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

    private CreateUserDTO userEmployer;
    private CreateUserDTO userAdmin;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userEmployer = new CreateUserDTO(
            "fakeemail@gmail.com",
            "contafake",
            "12345678",
            UserRoles.EMPLOYER
        );

        userAdmin = new CreateUserDTO(
            "fakeemail@gmail.com",
            "contafake",
            "12345678",
            UserRoles.ADMIN
        );
    }


    @Test
    @DisplayName("Should create new user when email does not exist")
    void testRegisterUserCase1() {
        

        when(userRepository.findByEmail(userEmployer.email())).thenReturn(Optional.empty());
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(userEmployer.password())).thenReturn("crypted-password");

        authService.registerUser(userEmployer);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        

        UserEntity userSaved = captor.getValue();
        assertEquals(userEmployer.email(), userSaved.getEmail());
        assertEquals(userEmployer.username(), userSaved.getUserName());
        assertEquals("crypted-password", userSaved.getPassword());
        assertEquals(userEmployer.role(), userSaved.getUserRole());
    }

    @Test
    @DisplayName("Should create new user EMPLOYER when email does not exist")
    void testRegisterUserCase2() {
        when(userRepository.findByEmail(userEmployer.email())).thenReturn(Optional.empty());
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(userEmployer.password())).thenReturn("crypted-password");

        authService.registerUser(userEmployer);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        

        UserEntity userSaved = captor.getValue();
        assertEquals(userEmployer.email(), userSaved.getEmail());
        assertEquals(userEmployer.username(), userSaved.getUserName());
        assertEquals("crypted-password", userSaved.getPassword());
        assertEquals(userEmployer.role(), userSaved.getUserRole());

        assertEquals(userSaved.getUserRole(), UserRoles.EMPLOYER);
    }

    @Test
    @DisplayName("Should create new user ADMIN when email does not exist")
    void testRegisterUserCase3() {
        when(userRepository.findByEmail(userAdmin.email())).thenReturn(Optional.empty());
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(userAdmin.password())).thenReturn("crypted-password");

        authService.registerUser(userAdmin);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        UserEntity userSaved = captor.getValue();
        assertEquals(userAdmin.email(), userSaved.getEmail());
        assertEquals(userAdmin.username(), userSaved.getUserName());
        assertEquals("crypted-password", userSaved.getPassword());
        assertEquals(userAdmin.role(), userSaved.getUserRole());

        assertEquals(userSaved.getUserRole(), UserRoles.ADMIN);
    }


    @Test
    @DisplayName("Should throw AuthException when email exists")
    void testRegisterUserCase4() {
        UserMapper userMapper = new UserMapper();

        when(userRepository.findByEmail(userAdmin.email())).thenReturn(Optional.of(userMapper.toEntity(userAdmin)));
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(userAdmin.password())).thenReturn("crypted-password");

        AuthException exception = Assertions.assertThrows(AuthException.class, () -> {
            authService.registerUser(userAdmin);
        });
        
        Assertions.assertEquals("A Account With This Email Aready Exists.", exception.getMessage());
    }

    @Test
    @DisplayName("Should authenticate user and return a TOKEN")
    void testAuthUserCase1() {
        LoginUserDTO login = new LoginUserDTO("emailafake@gmail.com", "12345678");

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(tokenService.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        JWTTokenResponseDTO response = authService.authUser(login);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.token());
    }
}
