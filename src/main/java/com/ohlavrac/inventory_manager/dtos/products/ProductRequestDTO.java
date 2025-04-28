package com.ohlavrac.inventory_manager.dtos.products;

import java.util.List;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;

public record ProductRequestDTO(
    String productName,
    String brand,
    int amount,
    double price,
    List<CategoryEntity> categories
) {
}
