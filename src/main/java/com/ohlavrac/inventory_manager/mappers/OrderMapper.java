package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.OrderEntity;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;

public class OrderMapper {
    public OrderResponseDTO toOrderResponseDTO (OrderEntity orderEntity) {
        return new OrderResponseDTO(
            orderEntity.getId(),
            orderEntity.getOrderName(),
            orderEntity.getQuantOrder(),
            orderEntity.getDescription(),
            orderEntity.getProductOrder()
        );
    }
}
