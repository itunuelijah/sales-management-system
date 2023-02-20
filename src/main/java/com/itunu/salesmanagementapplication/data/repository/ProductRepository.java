package com.itunu.salesmanagementapplication.data.repository;

import com.itunu.salesmanagementapplication.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}