package com.ohlavrac.inventory_manager.mappers;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.dtos.category.CategoryRequestDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryResponseDTO;

public class CategoryMapper {
    public CategoryEntity requestDtoToEntity(CategoryRequestDTO categoryRequestDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryRequestDTO.categoryName());
        categoryEntity.setProducts(categoryRequestDTO.products());

        return categoryEntity;
    }

    public CategoryResponseDTO entityToResponseDto(CategoryEntity categoryEntity) {
        return new CategoryResponseDTO(
            categoryEntity.getId(), 
            categoryEntity.getCategoryName(), 
            categoryEntity.getProducts()
        );
    }
}
