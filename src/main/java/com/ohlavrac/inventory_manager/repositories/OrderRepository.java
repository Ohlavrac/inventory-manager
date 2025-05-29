package com.ohlavrac.inventory_manager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ohlavrac.inventory_manager.domain.entities.OrderEntity;
import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity o SET o.orderStatus = :orderStatus WHERE o.id = :id")
    void updateTheOrderStatus(@Param(value = "id") UUID id, @Param(value = "orderStatus") OrderStatus orderStatus);
}
