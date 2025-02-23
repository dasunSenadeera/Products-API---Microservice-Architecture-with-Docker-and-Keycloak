package com.shop.productSetupService.controller;

import com.shop.productSetupService.model.Product;

import com.shop.productSetupService.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.dto.ProductRequest;
import org.shop.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/setup/products" )  // <-- Add this!
public class ProductControllerApiImpl implements ProductControllerApi
{
    private final ProductService productService;

    @Value( "${pagination.default-page}" )
    private int defaultPage;

    @Value( "${pagination.default-size}" )
    private int defaultSize;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request )
    {
        return ResponseEntity.ok( productService.createProduct( request ) );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<ProductResponse> getProductById( @PathVariable Long id )
    {
        return ResponseEntity.ok( productService.getProductById( id ) );
    }

    @GetMapping
    public Page<Product> getProducts(
            @RequestParam( defaultValue = "#{${pagination.default-page}}" ) int page,
            @RequestParam( defaultValue = "#{${pagination.default-size}}" ) int size )
    {
        return productService.getAllProducts( page, size );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<ProductResponse> updateProduct( @PathVariable Long id, @Valid @RequestBody ProductRequest request )
    {
        return ResponseEntity.ok( productService.updateProduct( id, request ) );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> deleteProduct( @PathVariable Long id )
    {
        productService.softDeleteProduct( id );
        return ResponseEntity.noContent().build();
    }

    @GetMapping( "/category/{category}" )
    public ResponseEntity<List<ProductResponse>> getByCategory( @PathVariable String category )
    {
        return ResponseEntity.ok( productService.getProductsByCategory( category ) );
    }

    @GetMapping( "/premium" )
    public ResponseEntity<List<ProductResponse>> getPremiumProducts()
    {
        return ResponseEntity.ok( productService.getPremiumProducts() );
    }
}