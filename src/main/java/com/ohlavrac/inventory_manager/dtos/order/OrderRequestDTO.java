package com.ohlavrac.inventory_manager.dtos.order;

import java.util.UUID;

public record OrderRequestDTO (
    String orderName,
    int quantOrder,
    String description,
    UUID productOrderUUID
) {
    
}
