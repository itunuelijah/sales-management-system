package com.itunu.salesmanagementapplication.web.exceptions;

public class ProductAlreadyInOrderException extends RuntimeException {
    public ProductAlreadyInOrderException(String message) {
        super(message);
    }
}