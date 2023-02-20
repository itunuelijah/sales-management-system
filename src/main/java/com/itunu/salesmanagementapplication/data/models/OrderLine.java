package com.itunu.salesmanagementapplication.data.models;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private int orderLineId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="price")
    private double price;

    @Column(name="total")
    private int total;


    public OrderLine(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderLine() {

    }
}