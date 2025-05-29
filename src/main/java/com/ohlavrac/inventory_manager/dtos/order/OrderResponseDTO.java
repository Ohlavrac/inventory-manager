package com.ohlavrac.inventory_manager.dtos.order;

import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;

public record OrderResponseDTO(
    UUID id,
    String orderName,
    int quantOrder,
    String description,
    ProductEntity productOrder,
    OrderStatus orderStatus
) {
    
}
