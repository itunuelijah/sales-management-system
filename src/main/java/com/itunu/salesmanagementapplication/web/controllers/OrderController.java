package com.itunu.salesmanagementapplication.web.controllers;

import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Order;
import com.itunu.salesmanagementapplication.service.OrderService;
import com.itunu.salesmanagementapplication.web.exceptions.BusinessLogicException;
import com.itunu.salesmanagementapplication.web.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }


    @PostMapping("/place_order")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto) {
        try {
            Order order = orderService.placeOrder(orderDto);
            return ResponseEntity.ok(order);
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/create_order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        try {
            Order order = orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.findOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}/date")
    public ResponseEntity<LocalDateTime> getOrderDate(@PathVariable Long orderId) {
        try {
            LocalDateTime date = orderService.getOrderDate(orderId);
            return ResponseEntity.ok(date);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


