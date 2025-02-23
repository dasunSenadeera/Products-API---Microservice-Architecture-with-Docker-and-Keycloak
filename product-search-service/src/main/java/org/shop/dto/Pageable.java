package org.shop.dto;

import lombok.Data;

@Data
public class Pageable {
    private int pageNumber;
    private int pageSize;
    private int offset;
    private boolean unpaged;
    private boolean paged;
}
