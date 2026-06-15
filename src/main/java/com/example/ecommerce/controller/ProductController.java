package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDetailResponse;
import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDetailResponse getProduct(
            @PathVariable Long id
    ){
        return productService.getProduct(id);
    }

    @GetMapping("/category/{slug}")
    public ProductPageResponse getProductsByCategory(
            @PathVariable String slug,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip
    ){
        return productService.getProductsByCategory(slug, limit, skip);
    }

}
