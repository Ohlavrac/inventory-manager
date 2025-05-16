package com.ohlavrac.inventory_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.category.CategoryNameResponseDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryRequestDTO;
import com.ohlavrac.inventory_manager.dtos.category.CategoryResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



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
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(categoryService.getCategoryDetailById(id));
        } catch (ResorceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequestDTO categoryData) {
        try {
            return ResponseEntity.ok().body(categoryService.updateCategory(id, categoryData));
        } catch (ResorceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}
