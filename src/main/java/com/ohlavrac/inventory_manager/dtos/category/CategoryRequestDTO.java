package com.ohlavrac.inventory_manager.dtos.category;

import java.util.Set;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

public record CategoryRequestDTO(
    String categoryName,
    Set<ProductEntity> products
) {
    
}
