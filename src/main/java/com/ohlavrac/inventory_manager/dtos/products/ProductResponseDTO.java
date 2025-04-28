package com.ohlavrac.inventory_manager.dtos.products;

import java.util.List;
import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;

public record ProductResponseDTO(
    UUID id,
    String productName,
    String brand,
    int amount,
    double price,
    List<CategoryEntity> categories
) {
    
}
