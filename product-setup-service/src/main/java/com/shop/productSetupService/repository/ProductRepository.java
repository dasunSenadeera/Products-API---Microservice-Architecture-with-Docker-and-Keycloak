package com.shop.productSetupService.repository;

import com.shop.productSetupService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p FROM Product p " +
                   "WHERE p.category = :category AND p.status = 'A'")
    List<Product> findByCategoryName(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.price >= 500 AND p.status = 'A'")
    List<Product> findAllPremiumProducts();
}
