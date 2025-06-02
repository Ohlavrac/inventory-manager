package com.ohlavrac.inventory_manager.domain.entities.user;

import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.enums.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;
    private String password;
    
    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRoles userRole;
}
