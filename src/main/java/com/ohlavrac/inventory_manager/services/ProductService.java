package com.ohlavrac.inventory_manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.domain.enums.StockLevel;
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

    public List<ProductResponseDTO> getProducts(StockLevel stock) {
        List<ProductEntity> products = new ArrayList<>();

        //TODO ORGANIZAR ISSO DE FORMA NORMAL.
        int LOWSOTCKLEVEL = 4; 

        switch (stock) {
            case ALL:
                products = productRepository.findAll();
                break;
            case LOW:
                products = productRepository.getProductByStock(LOWSOTCKLEVEL);
                break;
            default:
                products = productRepository.findAll();
                break;
        }

        List<ProductResponseDTO> response = products.stream().map(product -> productMapper.ToResponseDTO(product)).toList();
        return response;
    }

    public ProductResponseDTO getProductByID(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(
            () -> new ResorceNotFoundException("Product Not Found With ID: "+ id)
        );

        return productMapper.ToResponseDTO(productEntity);
    }

    public ProductEntity updateProduct(UUID productId, ProductRequestDTO productData) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResorceNotFoundException("Product Not Found With ID: "+ productId));
        


        product.setProductName(productData.productName().isBlank() ? product.getProductName() : productData.productName());
        product.setBrand(productData.brand().isBlank() ? product.getBrand() : productData.brand());
        product.setAmount(productData.amount() <=0 ? product.getAmount() : productData.amount());
        product.setPrice(productData.price() <= 0.0 ? product.getPrice() : productData.price());

        Set<CategoryEntity> categories = productData.categoriesIds().stream()
        .map(id -> categoryRepository.findById(id)
            .orElseThrow(() -> new ResorceNotFoundException("Category Not Found With ID: " + id)))
        .collect(Collectors.toSet());

        product.setCategories(categories);

        ProductEntity updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    public boolean deleteProduct(UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Product Not Found With ID: "+ id));
        
        productRepository.delete(product);
        return true;
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
