package com.itunu.salesmanagementapplication.data.dto;

import lombok.Data;


@Data
public class OrderLineDto {
    private Long orderLineId;
    private Long productId;
    private int quantity;
    private double price;
    private int total;
}
