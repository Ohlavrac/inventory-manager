package com.ohlavrac.inventory_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.products.ProductResponseDTO;
import com.ohlavrac.inventory_manager.mappers.ProductsMapper;
import com.ohlavrac.inventory_manager.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductsMapper productMapper;

    public ProductService(
        ProductRepository productRepository,
        ProductsMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDTO> getProducts() {
        List<ProductEntity> productsEntity = productRepository.findAll();
        List<ProductResponseDTO> productsResponse = productsEntity.stream().map(product -> productMapper.ToResponseDTO(product)).toList();

        return productsResponse;
    }
}
