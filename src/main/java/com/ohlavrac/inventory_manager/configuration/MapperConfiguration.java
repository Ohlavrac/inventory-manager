package com.ohlavrac.inventory_manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ohlavrac.inventory_manager.mappers.ProductsMapper;

@Configuration
public class MapperConfiguration {

    @Bean
    public ProductsMapper productsMapper() {
        return new ProductsMapper();
    }
}
