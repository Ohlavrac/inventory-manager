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

    

    @Test
    @DisplayName("Should return true when get User by email from DB")
    void testFindByEmailCase1() {
        String email = "testemail@gmail.com";

        CreateUserDTO userDTO = new CreateUserDTO(
            email,
            "fakeaccount",
            "12345678",
            UserRoles.ADMIN
        );

        this.createUser(userDTO);

        Optional<UserEntity> result = userRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testUpdateUserRole() {

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
