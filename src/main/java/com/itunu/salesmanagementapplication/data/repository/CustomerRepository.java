package com.itunu.salesmanagementapplication.data.repository;

import com.itunu.salesmanagementapplication.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}