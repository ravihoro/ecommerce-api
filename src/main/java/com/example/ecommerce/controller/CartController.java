package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.dto.UpdateCartItemRequest;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public CartResponse addToCart(
            @RequestHeader("Authorisation")
            String authHeader,

            @RequestBody
            AddToCartRequest request
    ){
        return cartService.addToCart(authHeader, request);
    }

    @GetMapping
    public CartResponse getCart(
            @RequestHeader("Authorization")
            String authHeader
    ){
        return cartService.getCart(authHeader);
    }

    @PutMapping("/items/{cartItemId}")
    public CartResponse updateCartItem (
            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long cartItemId,

            @RequestBody
            UpdateCartItemRequest request
    ){
        return cartService.updateCartItem(
                authHeader,
                cartItemId,
                request
        );
    }

}
