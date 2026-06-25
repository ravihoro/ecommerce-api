package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ReviewResponse;
import com.example.ecommerce.entity.ProductImage;
import org.springframework.stereotype.Component;

import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.entity.Product;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Product product, boolean isFavorite) {

        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDiscountPercentage(),
                product.getRating(),
                product.getBrand(),
                product.getThumbnail(),
                product.getCategory().getSlug()
        );
    }


    public ProductDetailResponse toDetailResponse(
            Product product,
            boolean isFavorite
    ) {

        return new ProductDetailResponse(

                product.getId(),

                product.getTitle(),

                product.getDescription(),

                product.getPrice(),

                product.getDiscountPercentage(),

                product.getRating(),

                product.getStock(),

                product.getBrand(),

                product.getSku(),

                product.getWeight(),

                product.getWidth(),

                product.getHeight(),

                product.getDepth(),

                product.getWarrantyInformation(),

                product.getShippingInformation(),

                product.getAvailabilityStatus(),

                product.getReturnPolicy(),

                product.getMinimumOrderQuantity(),

                product.getBarcode(),

                product.getQrCode(),

                product.getCategory().getSlug(),

                product.getThumbnail(),

                product.getImages()
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .toList(),

                product.getReviews()
                        .stream()
                        .map(review ->
                                new ReviewResponse(

                                        review.getRating(),

                                        review.getComment(),

                                        review.getReviewerEmail(),

                                        review.getReviewerName(),

                                        review.getReviewDate()
                                )
                        )
                        .toList()

        );
    }
}
