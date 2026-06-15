package com.example.ecommerce.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String title,
        BigDecimal price,
        BigDecimal discountPercentage,
        BigDecimal rating,
        String brand,
        String thumbnail
) {
}
