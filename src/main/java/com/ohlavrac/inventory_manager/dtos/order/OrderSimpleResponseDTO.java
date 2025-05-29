package com.ohlavrac.inventory_manager.dtos.order;

import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;

public record OrderSimpleResponseDTO(
    UUID id,
    String orderName,
    int quantOrder,
    String description,
    String productName,
    OrderStatus orderStatus
) {
    
}
