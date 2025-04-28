package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.products.ProductResponseDTO;

public class ProductsMapper {
    public ProductResponseDTO ToResponseDTO(ProductEntity productEntity) {
        return new ProductResponseDTO(
            productEntity.getId(), 
            productEntity.getProductName(), 
            productEntity.getBrand(), 
            productEntity.getAmount(), 
            productEntity.getPrice(), 
            productEntity.getCategories()
        );
    }
}
