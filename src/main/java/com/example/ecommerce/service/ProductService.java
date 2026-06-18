package com.example.ecommerce.service;


import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.dto.ReviewResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductImage;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        review.getReviewerName(),
                        review.getReviewDate()
                )).toList()
        );

    }


    public ProductPageResponse getProductsByCategory(String slug, int limit, int skip){

        int page = skip / limit;

        Pageable pageable =  PageRequest.of(page, limit);

        Page<Product> products = productRepository.findByCategorySlug(slug, pageable);

        return new ProductPageResponse(
                products.stream()
                        .map(product -> new ProductResponse(
                                product.getId(),
                                product.getTitle(),
                                product.getPrice(),
                                product.getDiscountPercentage(),
                                product.getRating(),
                                product.getBrand(),
                                product.getThumbnail(),
                                product.getCategory().getName()
                        ))
                        .toList(),
                products.getTotalElements(),
                skip,
                limit
        );
    }


}
