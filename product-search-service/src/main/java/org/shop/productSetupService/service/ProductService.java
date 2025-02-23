package org.shop.productSetupService.service;

import org.shop.dto.Product;
import org.shop.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {


    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${application.base-url}")
    private String baseUrl;
    private final WebClient webClient;

    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }



    public Mono<Product> getProductByIdFromApi(Long id) {
        return webClient.get()
                .uri(baseUrl + "/api/products/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    logger.warn("Product with ID {} not found. HTTP Status: {}", id, response.statusCode());
                    return Mono.error(new ResourceNotFoundException("Product", "id", id));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("Server error while fetching product with ID {}. HTTP Status: {}", id, response.statusCode());
                    return Mono.error(new RuntimeException("Server error while fetching product"));
                })
                .bodyToMono(Product.class)
                .doOnSuccess(product -> logger.info("Successfully fetched product: {}", product))
                .doOnError(error -> logger.error("Error fetching product with ID {}: {}", id, error.getMessage()));
    }
}
