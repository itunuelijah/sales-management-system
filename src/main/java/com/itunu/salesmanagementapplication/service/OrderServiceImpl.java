package com.itunu.salesmanagementapplication.service;


import com.itunu.salesmanagementapplication.data.dto.OrderDto;
import com.itunu.salesmanagementapplication.data.models.Customer;
import com.itunu.salesmanagementapplication.data.models.Order;

import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.data.repository.CustomerRepository;
import com.itunu.salesmanagementapplication.data.repository.OrderRepository;
import com.itunu.salesmanagementapplication.data.repository.ProductRepository;
import com.itunu.salesmanagementapplication.web.exceptions.BusinessLogicException;
import com.itunu.salesmanagementapplication.web.exceptions.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.order.created.topic}")
    private String kafkaOrderCreatedTopic;

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            CustomerRepository customerRepository, KafkaTemplate<String, Order> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }



    @Override
    public Order placeOrder(OrderDto orderDto) throws BusinessLogicException {
        try {
            log.info("Placing order --> {}", orderDto);
            return createOrder(orderDto);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw new BusinessLogicException("Failed to place order due to validation error.");
        } catch (DataIntegrityViolationException e) {
            throw new BusinessLogicException("Cannot place order because of concurrent order creation.");
        } catch (Exception e) {
            throw new BusinessLogicException("Failed to place order due to unexpected error.");
        }
    }

    @Override
    public Order createOrder(OrderDto orderDto) throws BusinessLogicException {

        Product product = getProductById(orderDto.getProductId());
        Customer customer = createCustomer(orderDto);
        Order order = buildOrder(orderDto, product, customer);
        Order savedOrder = saveOrder(order);
        updateProductStock(savedOrder);
        log.info("Sending to kafka ---> {}", orderDto);
        sendOrderToKafka(order);
        return savedOrder;
    }

    @Override
    public LocalDateTime getOrderDate(Long orderId) throws OrderNotFoundException {
        Order order = findOrderById(orderId);
        return order.getOrderDate();
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderNotFoundException {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " does not exist."));
    }


    @Override
    public void deleteOrder(Long orderId) {
        Order order = findOrderById(orderId);

        if (order != null) {
            Product product = productRepository.findById(order.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + order.getProduct().getProductId()));
            product.setNumberOfItemsInStock(product.getNumberOfItemsInStock() + order.getQuantity());
            productRepository.save(product);

            orderRepository.delete(order);
        }
    }

    private void updateProductStock(Order order) {
        Product product = order.getProduct();
        int orderQuantity = order.getQuantity();
        int stockQuantity = product.getNumberOfItemsInStock();
        if (stockQuantity < orderQuantity) {
            throw new IllegalArgumentException("Order quantity is greater than the number of items in stock.");
        }
        product.setNumberOfItemsInStock(stockQuantity - orderQuantity);
        productRepository.save(product);
    }

    private void sendOrderToKafka(Order order) {
        log.info("Sending placed order to kafka --> {}", order);
        kafkaTemplate.send(kafkaOrderCreatedTopic, order);
    }



    private Customer createCustomer(OrderDto orderDto) {
        Customer customer = new Customer();
        customer.setName(orderDto.getCustomerName());
        customer.setPhoneNumber(orderDto.getCustomerPhoneNumber());
        return customerRepository.save(customer);
    }

    private Order buildOrder(OrderDto orderDto, Product product, Customer customer) {
        if (orderDto == null) {
            throw new IllegalArgumentException("orderDto cannot be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("customer cannot be null");
        }

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(orderDto.getQuantity());
        order.setPrice(product.getPrice());
        order.setTotalPrice(product.getPrice() * orderDto.getQuantity());
        order.setCustomerName(customer.getName());
        order.setCustomerPhoneNumber(customer.getPhoneNumber());
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }


    private Order saveOrder(Order order) throws BusinessLogicException {
        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessLogicException("Cannot update order because of concurrent order update.");
        }
    }

}
