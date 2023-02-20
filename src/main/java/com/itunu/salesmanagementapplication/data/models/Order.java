package com.itunu.salesmanagementapplication.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JoinColumn(name = "customer_name", nullable = false)
    private String customerName;

    @JoinColumn(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @CreationTimestamp
    @Column(name = "order_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;


    public Order(Long id, Product product, String customerName, String customerPhoneNumber, int quantity , LocalDateTime orderDate) {
        this.id = id;
        this.product = product;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Order() {

    }

}
