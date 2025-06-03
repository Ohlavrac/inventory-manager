package com.ohlavrac.inventory_manager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.user.UserDetailsImpl;
import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        return new UserDetailsImpl(user);
    }
    
}
