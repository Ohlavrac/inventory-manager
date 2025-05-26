package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.products.ProductRequestDTO;
import com.ohlavrac.inventory_manager.dtos.products.ProductResponseDTO;
import com.ohlavrac.inventory_manager.services.ProductService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productData) {
        return ResponseEntity.ok().body(productService.createNewProduct(productData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody ProductRequestDTO productData) {
        return ResponseEntity.ok().body(productService.updateProduct(id, productData));
    }
    
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getProducts();
        return ResponseEntity.ok().body(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable UUID id) {
        ProductResponseDTO result = productService.getProductByID(id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(204).body("Product deleted");
    }
}
