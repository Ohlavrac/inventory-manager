package com.ohlavrac.inventory_manager.infra.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ohlavrac.inventory_manager.domain.entities.user.UserDetailsImpl;
import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(
        TokenService tokenService,
        UserRepository userRepository
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (!endpointIsPublic(request)) {
            String token = getToken(request);
            if (token!=null) {
                String subject = tokenService.getSubjectFromToken(token);
                UserEntity user = userRepository.findByEmail(subject).get();
                UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl.getUsername(), null, userDetailsImpl.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("Token not found");
            }
        }

        filterChain.doFilter(request, response);
    }
    
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader.isEmpty()) {
            return null;
        } else {
            return authorizationHeader.replace("Bearer", "");
        }
    }
    
    public boolean endpointIsPublic(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        
        return Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTH_NOT_REQUIRED).contains(requestUri);
    }
}
