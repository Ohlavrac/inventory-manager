package com.ohlavrac.inventory_manager.dtos.order;

import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

public record OrderResponseDTO(
    UUID id,
    String orderName,
    int quantOrder,
    String description,
    ProductEntity productOrder
) {
    
}
