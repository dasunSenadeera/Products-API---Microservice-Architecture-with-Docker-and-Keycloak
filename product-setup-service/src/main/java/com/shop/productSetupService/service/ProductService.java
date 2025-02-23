package com.shop.productSetupService.service;

import org.shop.exception.ResourceNotFoundException;
import com.shop.productSetupService.model.Product;
import com.shop.productSetupService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.shop.dto.ProductRequest;
import org.shop.dto.ProductResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;

    // Method to create a new product
    @Transactional
    public ProductResponse createProduct(org.shop.dto.ProductRequest request) {
        logger.info("Creating product with name: {}", request.getName());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        // Save product to the database
        Product savedProduct = productRepository.save(product);

        // Send the product ID to the ADD queue for further processing
        rabbitTemplate.convertAndSend("MESSAGE_QUEUE_PRODUCT_ADD", savedProduct.getId().toString());
        logger.info("Product created and ID sent to queue: {}", savedProduct.getId());

        return mapToResponse(savedProduct);
    }

    // Method to update an existing product
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        logger.info("Updating product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        // Save updated product to the database
        Product updatedProduct = productRepository.save(product);

        // Send updated product ID to the update queue
        rabbitTemplate.convertAndSend("MESSAGE_QUEUE_PRODUCT_UPDATE", updatedProduct.getId().toString());
        logger.info("Product updated and ID sent to queue: {}", updatedProduct.getId().toString());

        return mapToResponse(updatedProduct);
    }

    // Method to fetch product by its ID
    public ProductResponse getProductById(Long id) {
        logger.info("Fetching product by ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        return mapToResponse(product);
    }

    // Method to get all products with pagination
    public Page<Product> getAllProducts(int page, int size) {
        logger.info("Fetching all products with page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    // Method to soft delete a product by ID
    @Transactional
    public void softDeleteProduct(Long id) {
        logger.info("Soft deleting product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setStatus('D'); // Set product status to 'D' for deletion

        // Send product ID to the DELETE queue for processing
        rabbitTemplate.convertAndSend("MESSAGE_QUEUE_PRODUCT_DELETE", id.toString());
        logger.info("Product soft deleted and ID sent to queue: {}", id);

        productRepository.save(product);
    }

    // Method to get products by category
    public List<ProductResponse> getProductsByCategory(String category) {
        logger.info("Fetching products by category: {}", category);

        List<Product> products = productRepository.findByCategoryName(category);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Method to get all premium products
    public List<ProductResponse> getPremiumProducts() {
        logger.info("Fetching all premium products");

        List<Product> products = productRepository.findAllPremiumProducts();
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Helper method to map Product to ProductResponse
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getStatus()
        );
    }
}
