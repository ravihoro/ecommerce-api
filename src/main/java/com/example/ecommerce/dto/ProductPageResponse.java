package com.example.ecommerce.dto;

import java.util.List;

public record ProductPageResponse(
        List<ProductResponse> products,
        long total,
        int skip,
        int limit
) {
}
