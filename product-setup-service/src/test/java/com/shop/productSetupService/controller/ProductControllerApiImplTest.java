package com.shop.productSetupService.controller;

import com.shop.productSetupService.model.Product;
import com.shop.productSetupService.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.shop.dto.ProductRequest;
import org.shop.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerApiImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductControllerApiImpl productController;

    @Value("${pagination.default-page}")
    private int defaultPage = 0;

    @Value("${pagination.default-size}")
    private int defaultSize = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        ProductRequest productRequest =  ProductRequest.builder()
                                                .name("Product 1")
                                                .description("Description")
                                                .price( BigDecimal.valueOf( 100.0 ) )
                                                .category("Category")
                                                .build();

        ProductResponse productResponse =  ProductResponse.builder()
                                                  .id(1L)
                                                  .name("Product 1")
                                                  .description("Description")
                                                  .price( BigDecimal.valueOf( 100.0 ) )
                                                  .category("Category")
                                                  .build();

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.createProduct(productRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
    }

    @Test
    void getProductById() {
        ProductResponse productResponse =  ProductResponse.builder()
                                                  .id(1L)
                                                  .name("Product 1")
                                                  .description("Description")
                                                  .price( BigDecimal.valueOf( 100.0 ) )
                                                  .category("Category")
                                                  .build();

        when(productService.getProductById(1L)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
    }

    @Test
    void getProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description");
        product1.setPrice( BigDecimal.valueOf( 100.0 ) );
        product1.setCategory("Category");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description");
        product2.setPrice( BigDecimal.valueOf( 200.0 ) );
        product2.setCategory("Category");

        Page<Product> productPage = new PageImpl<>(Arrays.asList(product1, product2), PageRequest.of(0, 10), 2);

        when(productService.getAllProducts(defaultPage, defaultSize)).thenReturn(productPage);

        ResponseEntity<Page<Product>> response = ( ResponseEntity<Page<Product>> ) productController.getProducts(defaultPage, defaultSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull( response.getBody() ).getContent().size());
    }

    @Test
    void updateProduct() {
        ProductRequest productRequest =  ProductRequest.builder()
                                                .name("Updated Product")
                                                .description("Updated Description")
                                                .price( BigDecimal.valueOf( 150.0 ) )
                                                .category("Category")
                                                .build();

        ProductResponse productResponse =  ProductResponse.builder()
                                                  .id(1L)
                                                  .name("Updated Product")
                                                  .description("Updated Description")
                                                  .price( BigDecimal.valueOf( 150.0 ) )
                                                  .category("Category")
                                                  .build();

        when(productService.updateProduct(1L, productRequest)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.updateProduct(1L, productRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
    }

    @Test
    void deleteProduct() {
        doNothing().when(productService).softDeleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).softDeleteProduct(1L);
    }

    @Test
    void getByCategory() {
        ProductResponse productResponse =  ProductResponse.builder()
                                                  .id(1L)
                                                  .name("Product 1")
                                                  .description("Description")
                                                  .price( BigDecimal.valueOf( 100.0 ) )
                                                  .category("Category")
                                                  .build();

        when(productService.getProductsByCategory("Category")).thenReturn( Collections.singletonList( productResponse ) );

        ResponseEntity<List<ProductResponse>> response = productController.getByCategory("Category");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull( response.getBody() ).size());
    }

    @Test
    void getPremiumProducts() {
        ProductResponse productResponse =  ProductResponse.builder()
                                                  .id(1L)
                                                  .name("Premium Product")
                                                  .description("Description")
                                                  .price( BigDecimal.valueOf( 300.0 ) )
                                                  .category("Premium")
                                                  .build();

        when(productService.getPremiumProducts()).thenReturn( Collections.singletonList( productResponse ) );

        ResponseEntity<List<ProductResponse>> response = productController.getPremiumProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull( response.getBody() ).size());
    }
}
