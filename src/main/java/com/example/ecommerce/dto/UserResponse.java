package com.example.ecommerce.dto;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
