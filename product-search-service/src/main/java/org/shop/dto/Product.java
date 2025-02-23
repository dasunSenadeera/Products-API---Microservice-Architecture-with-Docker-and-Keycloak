package org.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private char status;
}
