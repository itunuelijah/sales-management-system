package com.itunu.salesmanagementapplication.web.controllers;

import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Order;
import com.itunu.salesmanagementapplication.service.OrderService;
import com.itunu.salesmanagementapplication.service.mapper.OrderMapper;
import com.itunu.salesmanagementapplication.web.exceptions.BusinessLogicException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void testPlaceOrder() {

        OrderDto orderCreateDto = new OrderDto();

        ResponseEntity<Order> responseEntity = restTemplate.postForEntity("/orders", orderCreateDto, Order.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Order order = responseEntity.getBody();
        assertNotNull(order);
    }


    @Test
    void testCreateOrder() {
        OrderDto orderCreateDto = new OrderDto();
        orderCreateDto.setCustomerId(1L);
        orderCreateDto.setProductId(2L);
        orderCreateDto.setQuantity(3);

        ResponseEntity<OrderDto> responseEntity = restTemplate.postForEntity("/orders", orderCreateDto, OrderDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Order createdOrder = orderMapper.fromOrderDTO(responseEntity.getBody());
        Order fetchedOrder = orderService.findOrderById(createdOrder.getId());

        assertEquals(createdOrder, fetchedOrder);
    }


    @Test
    void testGetOrderById() throws BusinessLogicException {

        Order order = orderService.placeOrder(new OrderDto());
        OrderDto orderDto = orderMapper.fromOrder(order);

        // send a GET request to /orders/{orderId}
        ResponseEntity<Order> responseEntity = restTemplate.getForEntity("/orders/" + orderDto.getId(), Order.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Order returnedOrder = responseEntity.getBody();
        assertNotNull(returnedOrder);
        assertEquals(orderDto.getId(), returnedOrder.getId());
    }

    @Test
    void testGetOrderDate() throws BusinessLogicException {

        Order order = orderService.placeOrder(new OrderDto());
        OrderDto orderDto = orderMapper.fromOrder(order);

        ResponseEntity<LocalDateTime> responseEntity = restTemplate.getForEntity("/orders/" + orderDto.getId() + "/date", LocalDateTime.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        LocalDateTime date = responseEntity.getBody();
        assertNotNull(date);
        assertEquals(orderDto.getOrderDate(), date);
    }

    @Test
    void testGetAllOrders() throws BusinessLogicException {
        orderService.placeOrder(new OrderDto());
        orderService.placeOrder(new OrderDto());

        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange("/orders", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // assert that the response body is not null and has a size of 2
        List<Order> orders = responseEntity.getBody();
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }


}