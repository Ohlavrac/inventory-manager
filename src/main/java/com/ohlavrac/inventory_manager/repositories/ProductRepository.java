package com.ohlavrac.inventory_manager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.amount = :amount WHERE p.id = :id")
    void updateProductAmount(@Param(value = "id") UUID id, @Param(value = "amount") int amount);
}
