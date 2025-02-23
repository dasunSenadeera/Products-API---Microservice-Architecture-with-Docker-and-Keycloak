package com.shop.productSetupService.configuration;

import com.shop.productSetupService.model.Product;
import com.shop.productSetupService.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader
{

    @Bean
    CommandLineRunner initDatabase( ProductRepository productRepository )
    {
        return args ->
        {
            if( productRepository.count() == 0 )
            {  // Avoid duplicate insertion
                List<Product> products = List.of(
                        new Product( null, "Laptop", "High-end gaming laptop", new BigDecimal( "1200.99" ), "Electronics", 'A' ),
                        new Product( null, "Smartphone", "Latest Android smartphone", new BigDecimal( "699.50" ), "Electronics", 'A' ),
                        new Product( null, "Coffee Maker", "Automatic coffee machine", new BigDecimal( "89.99" ), "Home Appliances", 'A' ),
                        new Product( null, "Desk Chair", "Ergonomic office chair", new BigDecimal( "149.99" ), "Furniture", 'A' ),
                        new Product( null, "Headphones", "Wireless noise-canceling headphones", new BigDecimal( "199.99" ), "Electronics", 'A' ),
                        new Product( null, "Tablet", "10-inch display tablet", new BigDecimal( "329.99" ), "Electronics", 'A' ),
                        new Product( null, "Monitor", "4K UHD monitor", new BigDecimal( "450.00" ), "Electronics", 'A' ),
                        new Product( null, "Keyboard", "Mechanical gaming keyboard", new BigDecimal( "99.99" ), "Electronics", 'A' ),
                        new Product( null, "Mouse", "Wireless gaming mouse", new BigDecimal( "49.99" ), "Electronics", 'A' ),
                        new Product( null, "Smartwatch", "Fitness tracking smartwatch", new BigDecimal( "249.99" ), "Wearable Tech", 'A' ),
                        new Product( null, "Backpack", "Water-resistant laptop backpack", new BigDecimal( "59.99" ), "Accessories", 'A' ),
                        new Product( null, "Sneakers", "Running shoes", new BigDecimal( "89.99" ), "Fashion", 'A' ),
                        new Product( null, "Sunglasses", "UV protection sunglasses", new BigDecimal( "39.99" ), "Fashion", 'A' ),
                        new Product( null, "Book", "Best-selling novel", new BigDecimal( "14.99" ), "Books", 'A' ),
                        new Product( null, "Desk Lamp", "LED adjustable desk lamp", new BigDecimal( "29.99" ), "Home", 'A' ),
                        new Product( null, "Smart Speaker", "Voice assistant speaker", new BigDecimal( "129.99" ), "Electronics", 'A' ),
                        new Product( null, "Bluetooth Speaker", "Portable Bluetooth speaker", new BigDecimal( "79.99" ), "Electronics", 'A' ),
                        new Product( null, "Fitness Tracker", "Heart rate & step tracking", new BigDecimal( "59.99" ), "Wearable Tech", 'A' ),
                        new Product( null, "External Hard Drive", "1TB USB 3.0 HDD", new BigDecimal( "99.99" ), "Storage", 'A' ),
                        new Product( null, "Water Bottle", "Insulated stainless steel bottle", new BigDecimal( "24.99" ), "Lifestyle", 'A' )
                );
                productRepository.saveAll( products );
                System.out.println( "Inserted 20 initial products into the database." );
            }
        };
    }
}
