package com.ohlavrac.inventory_manager.dtos.products;

import java.util.Set;
import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;

public record ProductResponseDTO(
    UUID id,
    String productName,
    String brand,
    int amount,
    double price,
    Set<CategoryEntity> categories
) {
    
}
