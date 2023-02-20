package com.itunu.salesmanagementapplication.data.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreateDto {
    private String name;
    private double price;
    private int NumberOfItemsInStock;
    private String description;
}

