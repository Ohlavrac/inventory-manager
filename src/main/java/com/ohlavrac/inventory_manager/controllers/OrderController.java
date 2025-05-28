package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.order.OrderRequestDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderSimpleResponseDTO;
import com.ohlavrac.inventory_manager.services.OrderService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



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
    
}
