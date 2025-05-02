package com.ohlavrac.inventory_manager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.CategoryEntity;
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
        List<CategoryEntity> categories = new ArrayList<CategoryEntity>();


        //VERIFY IF CATEGORY EXISTS IN DB (NEED VERIFY UPPERCASE LOWERCASE AND SPACES)
        for (int index = 0; index < productData.categories().size(); index++) {
            String categoryName = productData.categories().get(index).getCategoryName();

            if (!categoryRepository.findAll().isEmpty()) {
                if (!categoryRepository.findByCategoryName(categoryName).isPresent()) {
                    CategoryEntity newCategory = new CategoryEntity();
                    newCategory.setCategoryName(categoryName);
                    categoryRepository.save(newCategory);
                    categories.add(newCategory);
                } else {
                    continue;
                }
            } else {
                CategoryEntity newCategory = new CategoryEntity();
                newCategory.setCategoryName(categoryName);
                categoryRepository.save(newCategory);
                categories.add(newCategory);
            }
        }

        ProductEntity newProduct = productMapper.requestToEntity(productData);
        newProduct.setCategories(categories);

        ProductEntity saved = productRepository.save(newProduct);

        return productMapper.ToResponseDTO(saved);
    }
}
