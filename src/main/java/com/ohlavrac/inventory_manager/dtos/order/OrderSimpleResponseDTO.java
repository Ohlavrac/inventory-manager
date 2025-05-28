package com.ohlavrac.inventory_manager.dtos.order;

import java.util.UUID;

public record OrderSimpleResponseDTO(
    UUID id,
    String orderName,
    int quantOrder,
    String description,
    String productName
) {
    
}
