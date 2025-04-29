package com.ohlavrac.inventory_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.products.ProductRequestDTO;
import com.ohlavrac.inventory_manager.dtos.products.ProductResponseDTO;
import com.ohlavrac.inventory_manager.mappers.ProductsMapper;
import com.ohlavrac.inventory_manager.repositories.CategoryRepository;
import com.ohlavrac.inventory_manager.repositories.ProductRepository;

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

    public ProductResponseDTO createNewProduct(ProductRequestDTO productData) {
        ProductEntity productEntity = productMapper.requestToEntity(productData);


        //VERIFY IF CATEGORY EXISTS IN DB (NEED VERIFY UPPERCASE LOWERCASE AND SPACES)
        for (int index = 0; index < productData.categories().size(); index++) {
            String categoryName = productData.categories().get(index).getCategoryName().toLowerCase().strip();
            if (!categoryRepository.existsByCategoryName(categoryName) || categoryRepository.findAll().isEmpty()) {
                categoryRepository.save(productData.categories().get(index));
            } else {
                continue;
            }
        }

        ProductEntity newProduct = productRepository.save(productEntity);

        return productMapper.ToResponseDTO(newProduct);
    }
}
