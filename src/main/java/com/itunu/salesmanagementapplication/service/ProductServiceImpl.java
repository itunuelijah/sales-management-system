package com.itunu.salesmanagementapplication.service;

import com.itunu.salesmanagementapplication.data.dto.PriceUpdateDto;
import com.itunu.salesmanagementapplication.data.dto.ProductDto;
import com.itunu.salesmanagementapplication.data.models.OrderLine;
import com.itunu.salesmanagementapplication.data.models.Product;
import com.itunu.salesmanagementapplication.data.repository.OrderLineRepository;
import com.itunu.salesmanagementapplication.data.repository.ProductRepository;
import com.itunu.salesmanagementapplication.service.mapper.ProductMapper;
import com.itunu.salesmanagementapplication.web.exceptions.ProductAlreadyInOrderException;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final OrderLineRepository orderLineRepository;


    private final ProductMapper mapper;

    public ProductServiceImpl(ProductMapper mapper, ProductRepository productRepository, OrderLineRepository orderLineRepository) {
        this.mapper = mapper;
        this.productRepository = productRepository;
        this.orderLineRepository = orderLineRepository;
    }


    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = mapper.convertToEntity(productDto);
        Product savedProduct = saveProduct(product);
        return savedProduct;
    }


    @Override
    public Product updateProductPrice(PriceUpdateDto priceUpdateDto) throws ResourceNotFoundException, ProductAlreadyInOrderException {
        Product product = getProductById(priceUpdateDto.getProductId());
        validateProductAlreadyInOrder(product.getProductId());
        updatePrice(product, priceUpdateDto.getNewPrice());
        Product updatedProduct = saveProduct(product);
        return updatedProduct;
    }




    @Override
    public Product getProductById(Long productId) throws ResourceNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }


    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::convertToDto).collect(Collectors.toList());
        }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    private void validateProductAlreadyInOrder(Long productId) throws ProductAlreadyInOrderException {
        List<OrderLine> orderLines = orderLineRepository.findByProductId(productId);
        if (!orderLines.isEmpty()) {
            throw new ProductAlreadyInOrderException("Cannot update price, product already in order line");
        }
    }

    public void updatePrice(Product product, double newPrice) {
        product.setPrice(newPrice);
    }

}
