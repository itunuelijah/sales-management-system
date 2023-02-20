package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.CustomerDto;
import com.itunu.salesmanagementapplication.data.models.Customer;
import com.itunu.salesmanagementapplication.data.repository.CustomerRepository;
import com.itunu.salesmanagementapplication.service.mapper.CustomerMapper;
import com.itunu.salesmanagementapplication.web.exceptions.CustomerNotFoundException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map((Customer customer) -> mapper.convertToDto(customer))
                .collect(Collectors.toList());
    }



    @Override
    public Customer getCustomerById(Long customerId) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

    @Override
    public CustomerDto createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.convertToDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Customer updatedCustomer = mapper.convertToEntity(customerDto);
        updatedCustomer.setCustomerId(existingCustomer.getCustomerId());
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return mapper.convertToDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

}
