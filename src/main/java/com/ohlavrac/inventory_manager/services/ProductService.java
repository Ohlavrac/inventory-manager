package com.ohlavrac.inventory_manager.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.products.ProductRequestDTO;
import com.ohlavrac.inventory_manager.dtos.products.ProductResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.mappers.ProductsMapper;
import com.ohlavrac.inventory_manager.repositories.CategoryRepository;
import com.ohlavrac.inventory_manager.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductsMapper productMapper;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        ProductsMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDTO> getProducts() {
        List<ProductEntity> productsEntity = productRepository.findAll();
        List<ProductResponseDTO> productsResponse = productsEntity.stream().map(product -> productMapper.ToResponseDTO(product)).toList();

        return productsResponse;
    }

    @Transactional
    public ProductEntity createNewProduct(ProductRequestDTO productData) {
        ProductEntity newProduct = new ProductEntity();

        newProduct.setProductName(productData.productName());
        newProduct.setBrand(productData.brand());
        newProduct.setPrice(productData.price());
        newProduct.setAmount(productData.amount());

        Set<CategoryEntity> categories = productData.categoriesIds().stream()
        .map(id -> categoryRepository.findById(id)
            .orElseThrow(() -> new ResorceNotFoundException("Category Not Found With ID: " + id)))
        .collect(Collectors.toSet());

        newProduct.setCategories(categories);

        return  productRepository.save(newProduct);
    }
}
