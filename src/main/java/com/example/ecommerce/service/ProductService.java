package com.example.ecommerce.service;


import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ReviewResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductImage;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDetailResponse getProduct(Long id) {

        Product product = productRepository.findDetailById(id).orElseThrow();

        product.getImages().size();
        product.getReviews().size();

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
                product.getImages().stream().map(ProductImage::getImageUrl).toList(),
                product.getReviews().stream().map(review -> new ReviewResponse(
                        review.getRating(),
                        review.getComment(),
                        review.getReviewerEmail(),
                        review.getReviewerName()
                )).toList()
        );

    }

}
