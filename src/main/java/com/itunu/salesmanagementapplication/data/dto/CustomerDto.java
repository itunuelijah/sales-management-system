package com.itunu.salesmanagementapplication.data.dto;
import lombok.Data;

@Data
public class CustomerDto {
    private Long customerId;
    private String name;
    private String phoneNumber;

}