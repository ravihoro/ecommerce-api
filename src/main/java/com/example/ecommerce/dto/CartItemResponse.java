package com.example.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Integer productId,
        String title,
        BigDecimal price,
        String thumbnail,
        Integer quantity,
        BigDecimal subtotal
) {
}
