package com.ohlavrac.inventory_manager.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.category.CategoryNameResponseDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryRequestDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryResponseDTO;
import com.ohlavrac.inventory_manager.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryData) {
        CategoryResponseDTO result = categoryService.createCategory(categoryData);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryNameResponseDTO>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }
    
}
