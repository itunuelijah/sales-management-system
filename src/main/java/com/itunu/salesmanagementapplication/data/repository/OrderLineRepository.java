package com.itunu.salesmanagementapplication.data.repository;

import com.itunu.salesmanagementapplication.data.models.Customer;
import com.itunu.salesmanagementapplication.data.models.OrderLine;
import com.itunu.salesmanagementapplication.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByProductId(Long productId);
}