package com.itunu.salesmanagementapplication.web.controllers;
import com.itunu.salesmanagementapplication.data.dto.CustomerDto;
import com.itunu.salesmanagementapplication.data.models.Customer;
import com.itunu.salesmanagementapplication.service.CustomerService;
import com.itunu.salesmanagementapplication.service.mapper.CustomerMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService, CustomerMapper mapper) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody Customer customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
