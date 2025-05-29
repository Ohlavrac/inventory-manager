package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.domain.enums.OrderStatus;
import com.ohlavrac.inventory_manager.dtos.order.OrderRequestDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderSimpleResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderStatusRequestDTO;
import com.ohlavrac.inventory_manager.services.OrderService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController()
@RequestMapping("/api/order")
public class OrderController {
    OrderService orderService;

    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderData) {
        OrderResponseDTO response = orderService.createOrder(orderData);
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllOrders() {
        List<OrderSimpleResponseDTO> response = orderService.getAllOrders();

        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable UUID id) {
        OrderResponseDTO response = orderService.getOrderDetail(id);
        return ResponseEntity.ok().body(response);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatusToAccept(@PathVariable UUID id, @RequestBody OrderStatusRequestDTO status) {
        OrderSimpleResponseDTO response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok().body(response);
    }
}
