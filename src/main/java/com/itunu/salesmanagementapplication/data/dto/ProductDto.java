package com.itunu.salesmanagementapplication.data.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private int numberOfItemsInStock;
}
