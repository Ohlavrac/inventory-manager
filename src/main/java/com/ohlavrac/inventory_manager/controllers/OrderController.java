package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohlavrac.inventory_manager.dtos.order.OrderRequestDTO;
import com.ohlavrac.inventory_manager.dtos.order.OrderResponseDTO;
import com.ohlavrac.inventory_manager.services.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    
}
