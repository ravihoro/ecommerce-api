package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.dto.UpdateCartItemRequest;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public CartResponse addToCart(
            String authHeader,
            AddToCartRequest request
    ){

        String username = getUsername(authHeader);

        User user = userRepository.findByUsername(username).orElseThrow();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCreatedAt(LocalDateTime.now());
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(request.productId()).orElseThrow();

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if(optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.quantity());

            cartItemRepository.save(cartItem);
            return getCart(authHeader);
        }

        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);

        cartItem.setProduct(product);

        cartItem.setQuantity(request.quantity());

        cartItemRepository.save(cartItem);

        return getCart(authHeader);

    }

    @Transactional(readOnly = true)
    public CartResponse getCart(
            String authHeader
    ){

        String username = getUsername(authHeader);

        User user = userRepository.findByUsername(username).orElseThrow();

        Cart cart = cartRepository.findByUser(user).orElseThrow();

        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItem(
            String authHeader,
            Long cartItemId,
            UpdateCartItemRequest request
    ){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();

        cartItem.setQuantity(request.quantity());

        cartItemRepository.save(cartItem);

        return getCart(authHeader);
    }

    private String getUsername(String authHeader){
        String token = authHeader.replace("Bearer ", "");

        return jwtService.extractUsername(token);
    }

    private CartResponse mapToCartResponse(Cart cart){
        List<CartItemResponse> items = cart.getCartItems().stream().map(this::mapToCartItemResponse).toList();

        int totalItems = items.stream().mapToInt(CartItemResponse::quantity).sum();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), totalItems, totalPrice, items);
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem){
        Product product = cartItem.getProduct();

        BigDecimal subtotal = product.getPrice()
                .multiply(
                        BigDecimal.valueOf(
                                cartItem.getQuantity()
                        )
                );

        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getThumbnail(),
                cartItem.getQuantity(),
                subtotal
        );
    }



}


