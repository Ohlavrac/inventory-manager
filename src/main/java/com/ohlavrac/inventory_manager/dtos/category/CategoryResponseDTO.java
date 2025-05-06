package com.ohlavrac.inventory_manager.dtos.category;

import java.util.Set;
import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

public record CategoryResponseDTO (
    UUID id,
    String categoryName,
    Set<ProductEntity> products
) {
    
}
