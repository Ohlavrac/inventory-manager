package com.ohlavrac.inventory_manager.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.dtos.category.CategoryNameResponseDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryRequestDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
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

    public CategoryResponseDTO getCategoryDetailById(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Category Not Found With ID: "+ id));

        CategoryResponseDTO result = categoryMapper.entityToResponseDto(category);
        return result;
    }

    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryData) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Category Not Found With ID: " + id));

        categoryEntity.setCategoryName(categoryData.categoryName().isEmpty() ? categoryEntity.getCategoryName() : categoryData.categoryName());

        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        return categoryMapper.entityToResponseDto(updatedCategory);
    }

    public boolean categoryExists(String name) {
        boolean categoryExistsWithName = categoryRepository.existsByCategoryName(name);
        return categoryExistsWithName;
    }
}
