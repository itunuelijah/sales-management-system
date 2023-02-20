package com.itunu.salesmanagementapplication.web.controllers;

import com.itunu.salesmanagementapplication.data.dto.PriceUpdateDto;
import com.itunu.salesmanagementapplication.data.dto.ProductDto;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("create_product")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto){
        try{
            Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.ok().body(savedProduct);
        } catch ( IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/{productId}/price")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Long productId, @RequestBody PriceUpdateDto priceUpdateDto) {
        priceUpdateDto.setProductId(productId);
        Product updatedProduct = productService.updateProductPrice(priceUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }


    @GetMapping()
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
