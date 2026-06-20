package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDetailResponse getProduct(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            )
            String authHeader,

            @PathVariable Long id
    ){
        return productService.getProduct(authHeader,id);
    }

    @GetMapping("/category/{slug}")
    public ProductPageResponse getProductsByCategory(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            )
            String authHeader,

            @PathVariable String slug,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip
    ){
        return productService.getProductsByCategory(authHeader, slug, limit, skip);
    }

}
