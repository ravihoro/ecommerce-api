package com.example.ecommerce.dto;

public record ReviewResponse(
        Integer rating,
        String comment,
        String reviewerName,
        String reviewerEmail
) {}
