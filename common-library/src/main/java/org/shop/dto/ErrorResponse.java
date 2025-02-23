package org.shop.dto;

import java.util.Map;

// DTO
public record ErrorResponse(
        int statusCode,
        String message,
        Map<String, Object> details
) {}
