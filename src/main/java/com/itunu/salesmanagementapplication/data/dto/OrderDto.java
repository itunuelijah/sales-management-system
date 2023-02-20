package com.itunu.salesmanagementapplication.data.dto;


import com.itunu.salesmanagementapplication.data.models.OrderLine;
import com.itunu.salesmanagementapplication.data.models.Product;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderDto {

    private Long id;
    private Long productId;
    private Long customerId;
    private String customerPhoneNumber;
    private String CustomerName;
    private int quantity;
    private double price;
    private double totalPrice;
    public List<OrderLine> getOrderLines;
    private LocalDateTime orderDate;
    private double products;


    public OrderDto(Long id, String customerPhoneNumber, String customerName, int quantity) {
        this.id = id;
        this.customerPhoneNumber = customerPhoneNumber;
        CustomerName = customerName;
        this.quantity = quantity;
    }

    public OrderDto() {

    }


    public double getProducts() {
        return products;
    }
}


