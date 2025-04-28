package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.products.ProductRequestDTO;
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

    public ProductEntity requestToEntity (ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productRequestDTO.productName());
        productEntity.setBrand(productRequestDTO.brand());
        productEntity.setAmount(productRequestDTO.amount());
        productEntity.setPrice(productRequestDTO.price());
        productEntity.setCategories(productRequestDTO.categories());

        return productEntity;
    }
}
