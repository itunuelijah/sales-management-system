package com.itunu.salesmanagementapplication.data.repository;

import com.itunu.salesmanagementapplication.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}