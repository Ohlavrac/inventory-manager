package com.ohlavrac.inventory_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> createOrder(@RequestHeader(value = "Authorization") String token,  @RequestBody OrderRequestDTO orderData) {

        OrderResponseDTO response = orderService.createOrder(token, orderData);
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllOrders(
        @RequestParam(value = "status", required = false) OrderStatus orderStatus
    ) {
        
        List<OrderSimpleResponseDTO> response = orderService.getAllOrders(orderStatus);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(200).build();
    }
}
