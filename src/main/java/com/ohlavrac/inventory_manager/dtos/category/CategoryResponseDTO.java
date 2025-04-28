package com.ohlavrac.inventory_manager.dtos.category;

import java.util.List;
import java.util.UUID;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

public record CategoryResponseDTO (
    UUID id,
    String categoryName,
    List<ProductEntity> products
) {
    
}
