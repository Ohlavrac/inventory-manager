package com.ohlavrac.inventory_manager.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    boolean existsByCategoryName(String categoryName);

    Optional<CategoryEntity> findByCategoryName(String categoryName);
}
