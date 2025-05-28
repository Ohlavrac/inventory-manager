package com.ohlavrac.inventory_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.OrderEntity;
import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.dtos.order.OrderRequestDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderSimpleResponseDTO;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.exceptions.ResourceAmountExecption;
import com.ohlavrac.inventory_manager.mappers.OrderMapper;
import com.ohlavrac.inventory_manager.repositories.OrderRepository;
import com.ohlavrac.inventory_manager.repositories.ProductRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderMapper orderMapper;

    public OrderService(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderData) {
        OrderEntity order = new OrderEntity();

        ProductEntity product = productRepository.findById(orderData.productOrderUUID()).orElseThrow(() -> new ResorceNotFoundException("Product Not Found With ID: "+ orderData.productOrderUUID()));
        
        if (product.getAmount() < orderData.quantOrder()) {
            throw new ResourceAmountExecption("Not Enough Product in Sotck");
        } else {
            String defaultDescription = "Order: "+ product.getProductName() + " | Removed: " + orderData.quantOrder() + " from "+ product.getAmount() + "";

            order.setOrderName(orderData.orderName().isEmpty() ? product.getProductName() + "(" + (product.getAmount() - orderData.quantOrder()) + ")" : orderData.orderName());
            order.setQuantOrder(orderData.quantOrder());
            order.setDescription(orderData.description().isEmpty() ? defaultDescription : orderData.description());
            order.setProductOrder(product);

            OrderEntity orderSaved = orderRepository.save(order);

            productRepository.updateProductAmount(product.getId(), product.getAmount()-orderData.quantOrder());

            OrderResponseDTO response = new OrderResponseDTO(
                orderSaved.getId(),
                orderSaved.getOrderName(),
                orderSaved.getQuantOrder(),
                orderSaved.getDescription(),
                orderSaved.getProductOrder()
            );

            return response;
        }
    }

    public List<OrderSimpleResponseDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();

        List<OrderSimpleResponseDTO> response = orders.stream().map((order) -> new OrderSimpleResponseDTO(
            order.getId(),
            order.getOrderName(),
            order.getQuantOrder(),
            order.getDescription(),
            order.getProductOrder().getProductName()
        )).toList();
        return response;
    }
}
