package com.itunu.salesmanagementapplication.service.mapper;

import com.itunu.salesmanagementapplication.data.dto.CustomerDto;
import com.itunu.salesmanagementapplication.data.models.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);

        return customerDto;
    }


    public Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

}