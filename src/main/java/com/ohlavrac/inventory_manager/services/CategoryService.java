package com.ohlavrac.inventory_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.dtos.category.CategoryNameResponseDTO;
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

    public List<CategoryNameResponseDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        List<CategoryNameResponseDTO> result = categories.stream().map(category -> categoryMapper.entityToResponseNameDTO(category)).toList();
    
        return result;
    }

    public boolean categoryExists(String name) {
        boolean categoryExistsWithName = categoryRepository.existsByCategoryName(name);
        return categoryExistsWithName;
    }
}
