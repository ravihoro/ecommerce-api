package com.example.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailResponse(
        Long id,
        String title,
        String description,

        BigDecimal price,
        BigDecimal discountPercentage,
        BigDecimal rating,
        Integer stock,

        String brand,
        String sku,

        Integer weight,

        BigDecimal width,
        BigDecimal height,
        BigDecimal depth,

        String warrantyInformation,
        String shippingInformation,
        String availabilityStatus,

        String returnPolicy,
        Integer minimumOrderQuantity,

        String barcode,
        String qrCode,

        String category,

        String thumbnail,

        List<String> images,

        List<ReviewResponse> reviews

) {
}
