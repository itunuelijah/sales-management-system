package com.itunu.salesmanagementapplication.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="quantity")
    private int numberOfItemsInStock;

    @Column(name="description")
    private String description;


    public Product(Long productId, String name, double price, int numberOfItemsInStock, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.numberOfItemsInStock = numberOfItemsInStock;
        this.description = description;
    }

}



