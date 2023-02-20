package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.PriceUpdateDto;
import com.itunu.salesmanagementapplication.data.dto.ProductDto;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.web.exceptions.ProductAlreadyInOrderException;
import org.apache.kafka.common.errors.ResourceNotFoundException;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    Product updateProductPrice(PriceUpdateDto priceUpdateDto) throws ResourceNotFoundException, ProductAlreadyInOrderException;

    Product getProductById(Long productId) throws ResourceNotFoundException;

    List<ProductDto> getAllProducts();
}
