package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.PriceUpdateDto;
import com.itunu.salesmanagementapplication.data.dto.ProductDto;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.web.exceptions.ProductAlreadyInOrderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void testCreateProduct() {
        // Create a product DTO to use in the test
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setPrice(10.0);

        // Call the createProduct method
        Product createdProductDto = productService.createProduct(productDto);

        // Verify that the product was created successfully
        assertNotNull(createdProductDto);
        assertNotNull(createdProductDto.getProductId());
        assertEquals(productDto.getName(), createdProductDto.getName());
        assertEquals(productDto.getPrice(), createdProductDto.getPrice(), 0.001);
    }

    @Test
    public void testUpdateProductPrice() throws ResourceNotFoundException, ProductAlreadyInOrderException {
        // Create a product to use in the test
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setPrice(10.0);
        Product createdProductDto = productService.createProduct(productDto);

        // Create a price update DTO to use in the test
        PriceUpdateDto priceUpdateDto = new PriceUpdateDto();
        priceUpdateDto.setProductId(createdProductDto.getProductId());
        priceUpdateDto.setNewPrice(15.0);

        // Call the updateProductPrice method
        Product updatedProductDto = productService.updateProductPrice(priceUpdateDto);

        // Verify that the product price was updated successfully
        assertNotNull(updatedProductDto);
        assertEquals(createdProductDto.getProductId(), updatedProductDto.getProductId());
        assertEquals(createdProductDto.getName(), updatedProductDto.getName());
        assertEquals(priceUpdateDto.getNewPrice(), updatedProductDto.getPrice(), 0.001);
    }

    @Test
    public void testFindProductByIdNotFoundException() {
        try {
            productService.getProductById(-1L);
            fail("Expected ResourceNotFoundException but no exception was thrown");
        } catch (ResourceNotFoundException e) {

        }
    }


    @Test
    public void testGetAllProducts() {
        // Call the getAllProducts method
        List<ProductDto> products = productService.getAllProducts();

        // Verify that the list is not null and contains at least one product
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }


    @Test
    public void testUpdatePrice() {
        // Create a product to use in the test
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);

        // Call the updatePrice method
        productService.updatePrice(product, 15.0);

        // Verify that the product price was updated successfully
        assertEquals(15.0, product.getPrice(), 0.001);
    }

    @Test
    public void testSaveProduct() {
        // Create a product to use in the test
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Product Description");
        productDto.setPrice(10.0);

        // Call the createProduct method to save the product
        Product savedProductDto = productService.createProduct(productDto);

        // Retrieve the saved product from the repository
        Product retrievedProductDto = productService.getProductById(savedProductDto.getProductId());

        // Verify that the retrieved product matches the saved product
        assertEquals(savedProductDto.getProductId(), retrievedProductDto.getProductId());
        assertEquals(savedProductDto.getName(), retrievedProductDto.getName());
        assertEquals(savedProductDto.getDescription(), retrievedProductDto.getDescription());
        assertEquals(savedProductDto.getPrice(), retrievedProductDto.getPrice(), 0.001);
    }
}
