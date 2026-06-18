package com.example.ecommerce.dto;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String accessToken,
        String refreshToken
) {
}
