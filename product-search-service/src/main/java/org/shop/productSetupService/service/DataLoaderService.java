package org.shop.productSetupService.service;

import jakarta.annotation.PostConstruct;
import org.shop.dto.Product;
import org.shop.dto.ProductPageResponse;
import org.shop.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataLoaderService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${application.base-url}")
    private String baseUrl;

    private static Map<Long, Product> PRODUCTS_MAP = new ConcurrentHashMap<>(); // Changed to Long

    private final WebClient webClient;

    public DataLoaderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @PostConstruct
    public void init() {
        int size = 10; // Set your page size here
        loadAllProducts(size).subscribe(); // Initiate product loading asynchronously
    }

    private Mono<Void> loadAllProducts(int size) {
        return fetchAllPages(0, size).then();
    }

    private Mono<Void> fetchAllPages(int page, int size) {
        return webClient.get()
                .uri(baseUrl + "/api/products?page=" + page + "&size=" + size)
                .retrieve()
                .bodyToMono(ProductPageResponse.class)
                .flatMap(productPageResponse -> {
                    for (Product product : productPageResponse.getContent()) {
                        PRODUCTS_MAP.put(product.getId(), product);
                    }
                    if (productPageResponse.getContent().size() < size) {
                        return Mono.empty();
                    } else {
                        return fetchAllPages(page + 1, size);
                    }
                });
    }

    public static Optional<Product> getProductById(Long productId) {
        return Optional.ofNullable(PRODUCTS_MAP.get(productId));
    }

    public void cacheProduct(Product product) {
        PRODUCTS_MAP.put(product.getId(), product);
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
