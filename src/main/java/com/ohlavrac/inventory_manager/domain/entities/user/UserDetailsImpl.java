package com.ohlavrac.inventory_manager.domain.entities.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ohlavrac.inventory_manager.domain.enums.UserRoles;

import lombok.Getter;

@Getter
public class UserDetailsImpl implements UserDetails {

    private UserEntity userEntity; 

    public UserDetailsImpl(UserEntity user) {
        this.userEntity = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userEntity.getUserRole() == UserRoles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_EMPLOYER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        //APESAR DE SER GETUSERNAME DEVE RETORNAR O EMAIL POIS EU USO O EMAIL NOS TOKENS
        return userEntity.getEmail();
    }   
}
