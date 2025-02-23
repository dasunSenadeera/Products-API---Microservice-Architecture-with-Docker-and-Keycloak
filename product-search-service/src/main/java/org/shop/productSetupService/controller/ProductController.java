package org.shop.productSetupService.controller;

import org.shop.dto.Product;
import org.shop.dto.ProductResponse;
import org.shop.productSetupService.service.DataLoaderService;
import org.shop.productSetupService.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/search/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> getProductById(@PathVariable Long id) {

        Optional<Product> product = DataLoaderService.getProductById(id);

        if (product.isPresent()) {
            // If product is found in cache, return it as Mono
            return Mono.just(ResponseEntity.ok(mapToProductResponse(product.get())));
        } else {
            // If product is not found in cache, fetch it from the API
            Mono<Product> mono = productService.getProductByIdFromApi(id);
            return mono.map(fetchedProduct -> ResponseEntity.ok(mapToProductResponse(fetchedProduct)))
                    .defaultIfEmpty(ResponseEntity.notFound().build()); // Return 404 if API doesn't return product
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
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
