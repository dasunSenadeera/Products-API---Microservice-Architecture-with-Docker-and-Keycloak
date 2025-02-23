package com.shop.productSetupService.service;

import org.shop.exception.ResourceNotFoundException;
import com.shop.productSetupService.model.Product;
import com.shop.productSetupService.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shop.dto.ProductRequest;
import org.shop.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class )
class ProductServiceTest
{

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp()
    {
        product = new Product();
        product.setId( 1L );
        product.setName( "Test Product" );
        product.setDescription( "Test Description" );
        product.setPrice( BigDecimal.valueOf( 100.0 ) );
        product.setCategory( "Electronics" );
        product.setStatus( 'A' );

        productRequest = ProductRequest.builder()
                                       .name( "Updated Product" )
                                       .description( "Updated Description" )
                                       .price( BigDecimal.valueOf( 150.0 ) )
                                       .category( "Home" )
                                       .build();
    }

    @Test
    void testCreateProduct()
    {
        when( productRepository.save( any( Product.class ) ) ).thenReturn( product );

        ProductResponse response = productService.createProduct( productRequest );

        assertNotNull( response );
        assertEquals( "Test Product", response.getName() );
        verify( productRepository, times( 1 ) ).save( any( Product.class ) );
    }

    @Test
    void testUpdateProduct()
    {
        when( productRepository.findById( anyLong() ) ).thenReturn( Optional.of( product ) );
        when( productRepository.save( any( Product.class ) ) ).thenReturn( product );

        ProductResponse response = productService.updateProduct( 1L, productRequest );

        assertNotNull( response );
        assertEquals( "Updated Product", response.getName() );
        verify( productRepository, times( 1 ) ).save( any( Product.class ) );
    }

    @Test
    void testGetProductById()
    {
        when( productRepository.findById( anyLong() ) ).thenReturn( Optional.of( product ) );

        ProductResponse response = productService.getProductById( 1L );

        assertNotNull( response );
        assertEquals( "Test Product", response.getName() );
        verify( productRepository, times( 1 ) ).findById( 1L );
    }

    @Test
    void testGetProductById_NotFound()
    {
        when( productRepository.findById( anyLong() ) ).thenReturn( Optional.empty() );

        assertThrows( ResourceNotFoundException.class, () -> productService.getProductById( 1L ) );
    }

    @Test
    void testGetAllProducts()
    {
        Page<Product> page = new PageImpl<>( List.of( product ) );
        when( productRepository.findAll( any( Pageable.class ) ) ).thenReturn( page );

        Page<Product> result = productService.getAllProducts( 0, 10 );

        assertNotNull( result );
        assertEquals( 1, result.getTotalElements() );
        verify( productRepository, times( 1 ) ).findAll( any( Pageable.class ) );
    }

    @Test
    void testSoftDeleteProduct()
    {
        when( productRepository.findById( anyLong() ) ).thenReturn( Optional.of( product ) );
        when( productRepository.save( any( Product.class ) ) ).thenReturn( product );

        productService.softDeleteProduct( 1L );

        assertEquals( 'D', product.getStatus() );
        verify( productRepository, times( 1 ) ).save( product );
    }

    @Test
    void testGetProductsByCategory()
    {
        when( productRepository.findByCategoryName( anyString() ) ).thenReturn( Arrays.asList( product ) );

        List<ProductResponse> responseList = productService.getProductsByCategory( "Electronics" );

        assertNotNull( responseList );
        assertEquals( 1, responseList.size() );
        verify( productRepository, times( 1 ) ).findByCategoryName( "Electronics" );
    }

    @Test
    void testGetPremiumProducts()
    {
        when( productRepository.findAllPremiumProducts() ).thenReturn( Arrays.asList( product ) );

        List<ProductResponse> responseList = productService.getPremiumProducts();

        assertNotNull( responseList );
        assertEquals( 1, responseList.size() );
        verify( productRepository, times( 1 ) ).findAllPremiumProducts();
    }
}
