package com.example.ecommerce.service;


import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.dto.ReviewResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductImage;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.FavoriteRepository;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final AuthService authService;

    private final FavoriteRepository favoriteRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDetailResponse getProduct(
            String authHeader,
            Long id
    ) {

        Product product = productRepository
                .findDetailById(id)
                .orElseThrow();

        product.getImages().size();
        product.getReviews().size();

        boolean isFavorite =
                getFavoriteProductIds(authHeader)
                        .contains(product.getId());


        return productMapper.toDetailResponse(
            product, isFavorite
        );

    }


    public ProductPageResponse getProductsByCategory(String authHeader, String slug, int limit, int skip){

        int page = skip / limit;

        Pageable pageable = PageRequest.of(page, limit);

        Page<Product> products =
                productRepository.findByCategorySlug(
                        slug,
                        pageable
                );

        Set<Long> favoriteIds =
                getFavoriteProductIds(authHeader);

        return new ProductPageResponse(

                products.stream()
                        .map(product ->
                                productMapper.toProductResponse(
                                        product,
                                        favoriteIds.contains(
                                                product.getId()
                                        )
                                )
                        )
                        .toList(),

                products.getTotalElements(),

                skip,

                limit
        );
    }

    private Set<Long> getFavoriteProductIds(
            String authHeader
    ) {

        return authService.getCurrentUser(authHeader)
                .map(favoriteRepository::findProductIdsByUser)
                .orElse(Collections.emptySet());

    }

    @Transactional(readOnly = true)
    public ProductPageResponse searchProducts(
            String authHeader,
            String query,
            int limit,
            int skip
    ){
        Pageable pageable = PageRequest.of(skip/limit, limit);

        Page<Product> products = productRepository.searchProducts(query, pageable);

        Set<Long> favoriteIds = getFavoriteProductIds(authHeader);

        List<ProductResponse> productResponses = products.getContent().stream()
                .map(product ->
                    productMapper.toProductResponse(
                            product,
                            favoriteIds.contains(product.getId()))
                ).toList();

        return new ProductPageResponse(productResponses, products.getTotalElements(), skip, limit);
    }
}
