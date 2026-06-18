package com.example.ecommerce.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Integer rating,
        String comment,
        String reviewerName,
        String reviewerEmail,
        LocalDateTime reviewDate
) {}
