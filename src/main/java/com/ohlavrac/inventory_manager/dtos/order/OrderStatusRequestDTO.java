package com.ohlavrac.inventory_manager.dtos.order;

import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;

public record OrderStatusRequestDTO(
    OrderStatus orderStatus
) {
    
}
