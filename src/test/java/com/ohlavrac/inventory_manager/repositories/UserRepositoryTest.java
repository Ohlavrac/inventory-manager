package com.ohlavrac.inventory_manager.repositories;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.UserRoles;
import com.ohlavrac.inventory_manager.dtos.users.CreateUserDTO;

import jakarta.persistence.EntityManager;

@DataJpaTest //DIZ PARA O SPRING QUE ESSA E UMA CLASS DE TEST QUE TESTA O JPA
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    String email = "testemail@gmail.com";

    CreateUserDTO userDTOAdmin = new CreateUserDTO(
        email,
        "fakeaccount",
        "12345678",
        UserRoles.ADMIN
    );

    CreateUserDTO userDTOEmployer = new CreateUserDTO(
        email,
        "fakeaccount",
        "12345678",
        UserRoles.EMPLOYER
    );

    @Test
    @DisplayName("Should return true when get User by email from DB")
    void testFindByEmailCase1() {
        this.createUser(userDTOAdmin);

        Optional<UserEntity> result = userRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should return false when cant find User by email from DB")
    void testFindByEmailCase2() {
        String email = "testemail@gmail.com";

        Optional<UserEntity> result = userRepository.findByEmail(email);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should return true when role updated to EMPLOYER")
    void testUpdateUserRoleCase1() {

        this.createUser(userDTOAdmin);

        Optional<UserEntity> adminUser = userRepository.findByEmail(email);
        userRepository.updateUserRole(UserRoles.EMPLOYER, adminUser.get().getId());

        Optional<UserEntity> result = userRepository.findByEmail(email);

        assertThat(result.get().getUserRole()).isEqualTo(UserRoles.EMPLOYER);
    }

    private UserEntity createUser(CreateUserDTO userDTO) {
        UserEntity newUser = new UserEntity();

        newUser.setEmail(userDTO.email());
        newUser.setUserName(userDTO.username());
        newUser.setPassword(userDTO.password());
        newUser.setUserRole(userDTO.role());

        this.entityManager.persist(newUser);

        return newUser;
    }
}
