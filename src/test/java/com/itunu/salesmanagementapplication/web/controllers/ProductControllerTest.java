package com.itunu.salesmanagementapplication.web.controllers;

import com.itunu.salesmanagementapplication.data.dto.PriceUpdateDto;
import com.itunu.salesmanagementapplication.data.dto.ProductDto;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Test
    void testCreateProduct() {

        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("This is a test product.");
        productDto.setPrice(10.0);

        // Send a POST request to the endpoint with the ProductDto payload
        ResponseEntity<ProductDto> response = restTemplate.postForEntity("/api/products", productDto, ProductDto.class);

        // Check that the response status code is 201 (Created)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Extract the created ProductDto from the response body
        ProductDto createdProduct = response.getBody();


        assertNotNull(createdProduct.getProductId());


        assertEquals(productDto.getName(), createdProduct.getName());
        assertEquals(productDto.getDescription(), createdProduct.getDescription());
        assertEquals(productDto.getPrice(), createdProduct.getPrice(), 0.01);
    }

    @Test
    void testUpdateProductPrice() {
        // Create a product with an initial price
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("This is a test product.");
        productDto.setPrice(10.0);
        Product createdProduct = productService.createProduct(productDto);

        // Update the product's price
        PriceUpdateDto priceUpdateDto = new PriceUpdateDto();
        priceUpdateDto.setProductId(createdProduct.getProductId());
        priceUpdateDto.setNewPrice(20.0);
        ResponseEntity<ProductDto> response = restTemplate.exchange("/api/products/{productId}/price", HttpMethod.PATCH, new HttpEntity<>(priceUpdateDto), ProductDto.class, createdProduct.getProductId());


        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductDto updatedProduct = response.getBody();

        assertEquals(priceUpdateDto.getNewPrice(), updatedProduct.getPrice(), 0.01);
    }

    @Test
    void testGetAllProducts() {
        // Create some products
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Test Product 1");
        productDto1.setDescription("This is test product 1.");
        productDto1.setPrice(10.0);
        Product createdProduct1 = productService.createProduct(productDto1);

        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Test Product 2");
        productDto2.setDescription("This is test product 2.");
        productDto2.setPrice(20.0);
        Product createdProduct2 = productService.createProduct(productDto2);

        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity("/api/products", ProductDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductDto[] productDtos = response.getBody();

        assertEquals(2, productDtos.length);
        assertEquals((createdProduct1), productDtos[0]);
        assertEquals((createdProduct2), productDtos[1]);
    }
}