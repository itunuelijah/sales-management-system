package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Order;
import com.itunu.salesmanagementapplication.web.exceptions.BusinessLogicException;
import com.itunu.salesmanagementapplication.web.exceptions.OrderNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order placeOrder(OrderDto orderDto) throws BusinessLogicException;

    Order createOrder(OrderDto orderDto) throws BusinessLogicException;

    LocalDateTime getOrderDate(Long orderId) throws OrderNotFoundException;

    List<Order> getAllOrders();

    Order findOrderById(Long orderId) throws OrderNotFoundException;

    void deleteOrder(Long orderId);
}
