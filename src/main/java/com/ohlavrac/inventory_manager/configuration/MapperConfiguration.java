package com.ohlavrac.inventory_manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ohlavrac.inventory_manager.mappers.CategoryMapper;
import com.ohlavrac.inventory_manager.mappers.OrderMapper;
import com.ohlavrac.inventory_manager.mappers.ProductsMapper;
import com.ohlavrac.inventory_manager.mappers.UserMapper;

@Configuration
public class MapperConfiguration {

    @Bean
    public ProductsMapper productsMapper() {
        return new ProductsMapper();
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
