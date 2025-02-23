package com.shop.productSetupService.controller;


import com.shop.productSetupService.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.shop.dto.ProductRequest;
import org.shop.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product API", description = "Endpoints for managing products")
public interface ProductControllerApi {

    @Operation(summary = "Create a new product")
    ResponseEntity<org.shop.dto.ProductResponse> createProduct(@RequestBody org.shop.dto.ProductRequest request);


    @Operation(summary = "Get a product by ID")
    ResponseEntity<ProductResponse> getProductById(@PathVariable Long id);

    @Operation(summary = "Get all products with pagination")
    Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Update a product by ID")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request);

    @Operation(summary = "Soft delete a product by ID")

    ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    @Operation(summary = "Get products by category")

    ResponseEntity<List<ProductResponse>> getByCategory(@PathVariable String category);

    @Operation(summary = "Get premium products")

    ResponseEntity<List<ProductResponse>> getPremiumProducts();
}
