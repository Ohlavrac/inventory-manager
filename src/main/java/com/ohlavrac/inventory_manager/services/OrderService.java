package com.ohlavrac.inventory_manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ohlavrac.inventory_manager.domain.entities.OrderEntity;
import com.ohlavrac.inventory_manager.domain.entities.ProductEntity;
import com.ohlavrac.inventory_manager.domain.entities.user.UserEntity;
import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;
import com.ohlavrac.inventory_manager.dtos.order.OrderRequestDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderSimpleResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderStatusRequestDTO;
import com.ohlavrac.inventory_manager.exceptions.AuthException;
import com.ohlavrac.inventory_manager.exceptions.DeleteException;
import com.ohlavrac.inventory_manager.exceptions.OrderUpdateException;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.exceptions.ResourceAmountExecption;
import com.ohlavrac.inventory_manager.infra.security.TokenService;
import com.ohlavrac.inventory_manager.mappers.OrderMapper;
import com.ohlavrac.inventory_manager.repositories.OrderRepository;
import com.ohlavrac.inventory_manager.repositories.ProductRepository;
import com.ohlavrac.inventory_manager.repositories.UserRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TokenService tokenService;
    private final OrderMapper orderMapper;

    public OrderService(
        OrderRepository orderRepository,
        UserRepository userRepository,
        ProductRepository productRepository,
        TokenService tokenService,
        OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.tokenService = tokenService;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDTO createOrder(String token, OrderRequestDTO orderData) {
        OrderEntity order = new OrderEntity();

        String userAuthEmail = tokenService.getSubjectFromToken(token);
        UserEntity userAuthData = userRepository.findByEmail(userAuthEmail).orElseThrow(() -> new AuthException("Authentication Error"));

        ProductEntity product = productRepository.findById(orderData.productOrderUUID()).orElseThrow(() -> new ResorceNotFoundException("Product Not Found With ID: "+ orderData.productOrderUUID()));
        
        if (product.getAmount() < orderData.quantOrder()) {
            throw new ResourceAmountExecption("Not Enough Product in Sotck");
        } else {
            String defaultDescription = "Order: "+ product.getProductName() + " | Removed: " + orderData.quantOrder() + " from "+ product.getAmount() + "";

            order.setOrderName(orderData.orderName().isEmpty() ? product.getProductName() + "(" + (product.getAmount() - orderData.quantOrder()) + ")" : orderData.orderName());
            order.setQuantOrder(orderData.quantOrder());
            order.setDescription(orderData.description().isEmpty() ? defaultDescription : orderData.description());
            order.setProductOrder(product);
            order.setOrderStatus(OrderStatus.PENDING);
            order.setCreator(userAuthData);

            OrderEntity orderSaved = orderRepository.save(order);

            //JUST UPDATE PRODUCT AMOUNT WHEN ORDER IS ACCEPTED
            //productRepository.updateProductAmount(product.getId(), product.getAmount()-orderData.quantOrder());

            OrderResponseDTO response = new OrderResponseDTO(
                orderSaved.getId(),
                orderSaved.getOrderName(),
                orderSaved.getQuantOrder(),
                orderSaved.getDescription(),
                orderSaved.getProductOrder(),
                orderSaved.getOrderStatus()
            );

            return response;
        }
    }

    public List<OrderSimpleResponseDTO> getAllOrders(OrderStatus status){
        List<OrderEntity> orders = new ArrayList<>();

        if (status == null) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.getOrderByStatus(status);
        }

        List<OrderSimpleResponseDTO> response = orders.stream().map((order) -> new OrderSimpleResponseDTO(
            order.getId(),
            order.getOrderName(),
            order.getQuantOrder(),
            order.getDescription(),
            order.getProductOrder().getProductName(),
            order.getOrderStatus()
        )).toList();
        return response;
    }

    public OrderResponseDTO getOrderDetail(UUID id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Order Not Found With ID: "+ id));

        OrderResponseDTO response = orderMapper.toOrderResponseDTO(order);
        return response;
    }

    public OrderSimpleResponseDTO updateOrderStatus(UUID id, OrderStatusRequestDTO status) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Order Not Found With ID: "+ id));

        if (order.getOrderStatus() == OrderStatus.CANCELED || order.getOrderStatus() == OrderStatus.REFUSED || order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new OrderUpdateException("The Status ("+ order.getOrderStatus() +") Order Can't Be Updated");
        } else {
            orderRepository.updateTheOrderStatus(id, status.orderStatus());
        }

        if (status.orderStatus() == OrderStatus.PENDING || 
            status.orderStatus() == OrderStatus.REFUSED || 
            status.orderStatus() == OrderStatus.CANCELED && 
            order.getOrderStatus() == OrderStatus.ACCEPTED
        ) {
            productRepository.updateProductAmount(order.getProductOrder().getId(), order.getProductOrder().getAmount()+order.getQuantOrder());
        } else {
            if (order.getProductOrder().getAmount() < order.getQuantOrder()) {
                throw new ResourceAmountExecption("Not Enough Product in Sotck");
            }
            
            productRepository.updateProductAmount(order.getProductOrder().getId(), order.getProductOrder().getAmount()-order.getQuantOrder());
        }

        OrderEntity orderResult = orderRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Order Not Found With ID: "+ id));

        return new OrderSimpleResponseDTO(
            orderResult.getId(),
            orderResult.getOrderName(),
            orderResult.getQuantOrder(),
            orderResult.getDescription(),
            orderResult.getOrderName(),
            status.orderStatus()
        );
    }

    public void deleteOrder(UUID id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Order Not Found With ID: "+ id));

        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new DeleteException("The Order ("+ order.getOrderStatus() +") Cant Be Canceleted/Delete");
        } else if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            productRepository.updateProductAmount(order.getProductOrder().getId(), order.getProductOrder().getAmount()+order.getQuantOrder());
            orderRepository.delete(order);
        } else {
            orderRepository.delete(order);
        }
    }
}
