package com.ohlavrac.inventory_manager.dtos.category;

import java.util.List;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;

public record CategoryRequestDTO(
    String categoryName,
    List<ProductEntity> products
) {
    
}
