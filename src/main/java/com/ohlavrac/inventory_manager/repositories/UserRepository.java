package com.ohlavrac.inventory_manager.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.UserRoles;

import jakarta.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE UserEntity u SET u.userRole = :userRole WHERE u.id = :id")
    void updateUserRole(@Param(value="userRole") UserRoles userRole, @Param(value="id") UUID id);
}
