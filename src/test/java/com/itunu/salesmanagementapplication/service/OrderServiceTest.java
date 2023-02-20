package com.itunu.salesmanagementapplication.service;

import java.time.LocalDateTime;

import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Customer;
import com.itunu.salesmanagementapplication.data.models.Order;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.web.exceptions.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Test
    void testBuildOrder() throws BusinessLogicException {
        // Create a test OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setProductId(2L);
        orderDto.setQuantity(5);

        // Retrieve the test Product object from the ProductService
        Product product = productService.getProductById(2L);

        // Retrieve the test Customer object from the CustomerService
        Customer customer = customerService.getCustomerById(1L);

        // Call the buildOrder() method with the test OrderDto, Product, and Customer objects
        Order order = orderService.createOrder(orderDto);

        // Verify that the Order object was created correctly
        assertNotNull(order);
        assertEquals(order.getProduct(), product);
        assertEquals(order.getQuantity(), orderDto.getQuantity());
        assertEquals(order.getPrice(), product.getPrice(), 0.001);
        assertEquals(order.getTotalPrice(), product.getPrice() * orderDto.getQuantity(), 0.001);
        assertEquals(order.getCustomerName(), customer.getName());
        assertEquals(order.getCustomerPhoneNumber(), customer.getPhoneNumber());
        assertNotNull(order.getOrderDate());
    }


}


//    @Test
//    void testPlaceOrderWithNullOrderDto() {
//        // Arrange
//        OrderDto orderDto = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(orderDto));
//    }
//
//    @Test
//    void testPlaceOrderWithConcurrentOrderCreation() {
//        // Arrange
//        OrderDto orderDto = new OrderDto(1L, "Test Customer", "1234567890", 10);
//        when(orderRepository.save(any(Order.class))).thenThrow(DataIntegrityViolationException.class);
//
//        // Act & Assert
//        assertThrows(BusinessLogicException.class, () -> orderService.placeOrder(orderDto));
//    }
//
//
//
//        @Test
//        public void testCreateOrder() throws BusinessLogicException {
//            // create a product for the order
//            Product product = new Product();
//            product.setName("Product A");
//            product.setPrice(10.0);
//
//            // create a customer for the order
//            Customer customer = new Customer();
//            customer.setName("John Doe");
//
//            // create an order dto
//            OrderDto orderDto = new OrderDto();
//            orderDto.setProductId(product.getProductId());
//            orderDto.setQuantity(2);
//            orderDto.setCustomerName(customer.getName());
//
//
//
//            // create the order using the order service
//            Order order = orderService.createOrder(orderDto);
//
//            // assert that the order was created successfully
//            assertNotNull(order.getId());
//            assertEquals(product.getName(), order.getProduct().getName());
//            assertEquals(customer.getName(), order.getCustomerName());
//            assertEquals(orderDto.getQuantity(), order.getQuantity());
//            assertEquals(orderDto.getPrice(), order.getPrice(), 0.0);
//            assertEquals(orderDto.getTotalPrice(), order.getTotalPrice(), 0.0);
//            assertNotNull(order.getOrderDate());
//        }
//
//    @Test
//    void placeOrder_invalidOrder_shouldThrowBusinessLogicException() {
//        OrderDto orderDto = new OrderDto();
//        assertThrows(BusinessLogicException.class, () -> orderService.placeOrder(orderDto));
//    }
//
//
//    @Test
//    void getAllOrders_shouldReturnAllOrders() {
//        List<Order> orders = orderService.getAllOrders();
//        assertEquals(orderRepository.count(), orders.size());
//    }
//
//    @Test
//    void findOrderById_validOrderId_shouldReturnOrder() throws OrderNotFoundException {
//        Long orderId = 1L;
//        Order order = orderService.findOrderById(orderId);
//
//        assertNotNull(order);
//        assertEquals(orderId, order.getId());
//    }
//
//    @Test
//    void findOrderById_invalidOrderId_shouldThrowOrderNotFoundException() {
//        Long orderId = 999L;
//        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(orderId));
//    }
//
//
//
//    @Test
//    void deleteOrder_validOrderId_shouldDeleteOrder() throws OrderNotFoundException {
//        Long orderId = 1L;
//        orderService.deleteOrder(orderId);
//        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(orderId));
//    }
//
//
//
//    @Test
//    void testGetAllOrders() {
//        // Act
//        List<Order> orders = orderService.getAllOrders();
//
//        // Assert
//        assertNotNull(orders);
//        assertEquals(1, orders.size());
//    }
//
//    @Test
//    void testFindOrderById() throws OrderNotFoundException {
//
//        Long orderId = 1L;
//        Order foundOrder = orderService.findOrderById(orderId);
//
//        assertNotNull(foundOrder);
//        assertEquals(orderId, foundOrder.getId());
//    }
//
//    @Test
//    void testDeleteOrder() throws OrderNotFoundException {
//        Long orderId = 1L;
//
//        orderService.deleteOrder(orderId);
//
//        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(orderId));
//    }
//
//}