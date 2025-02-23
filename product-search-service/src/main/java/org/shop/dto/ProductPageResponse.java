package org.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductPageResponse {
    private List<Product> content;
    private Pageable pageable;
    private int totalElements;
    private int totalPages;
    private boolean last;
    private int size;
    private int number;
    private boolean first;
    private int numberOfElements;
    private boolean empty;
}
