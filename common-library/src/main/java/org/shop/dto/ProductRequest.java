package org.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {

    @NotBlank
    private String name;
    private String description;
    @Positive
    private BigDecimal price;
    private String category; // Multiple categories

}
