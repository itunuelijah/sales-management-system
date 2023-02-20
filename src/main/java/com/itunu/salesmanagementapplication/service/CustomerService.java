package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.CustomerDto;
import com.itunu.salesmanagementapplication.data.models.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    Customer getCustomerById(Long id);

    CustomerDto createCustomer(Customer customerDto);

    CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    void deleteCustomer(Long id);
}
