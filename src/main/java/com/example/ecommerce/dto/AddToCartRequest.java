package com.example.ecommerce.dto;

public record AddToCartRequest(
        Long productId,
        Integer quantity
) {
}
