package com.ohlavrac.inventory_manager.services;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.dtos.category.CategoryRequestDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryResponseDTO;
import com.ohlavrac.inventory_manager.mappers.CategoryMapper;
import com.ohlavrac.inventory_manager.repositories.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(
        CategoryRepository categoryRepository,
        CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryData) {
        CategoryEntity categoryEntity = categoryMapper.requestDtoToEntity(categoryData);
        CategoryEntity newCategory = categoryRepository.save(categoryEntity);

        return categoryMapper.entityToResponseDto(newCategory);
    }

    public boolean categoryExists(String name) {
        boolean categoryExistsWithName = categoryRepository.existsByCategoryName(name);
        return categoryExistsWithName;
    }
}
