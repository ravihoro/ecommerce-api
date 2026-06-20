package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{productId}")
    public boolean addFavorite(
            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long productId
    ){
        return favoriteService.addFavorite(authHeader, productId);
    }

    @DeleteMapping("/{productId}")
    public boolean removeFavorite(

            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long productId
    ) {

        return favoriteService.removeFavorite(
                authHeader,
                productId
        );
    }

    @GetMapping
    public ProductPageResponse getFavorites(
            @RequestHeader("Authorization")
            String authHeader,

            @RequestParam(defaultValue = "20")
            int limit,

            @RequestParam(defaultValue = "0")
            int skip

    ){
        return favoriteService.getFavorites(
                authHeader,
                limit,
                skip
        );
    }
}
