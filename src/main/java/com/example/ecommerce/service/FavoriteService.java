package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductPageResponse;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.entity.Favorite;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.FavoriteRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;

    private final ProductMapper productMapper;
    private final AuthService authService;

    public boolean addFavorite(
            String authHeader,
            Long productId
    ){
        User user = authService.requireCurrentUser(authHeader);

        Product product  = productRepository.findById(productId).orElseThrow();

        if (favoriteRepository.findByUserAndProduct(user, product).isPresent()) {
            return true;
        }

        Favorite newFavorite = new Favorite();

        newFavorite.setUser(user);

        newFavorite.setProduct(product);

        newFavorite.setCreatedAt(LocalDateTime.now());

        favoriteRepository.save(newFavorite);

        return true;
    }

    @Transactional(readOnly = true)
    public ProductPageResponse getFavorites(
            String authHeader,
            int limit,
            int skip
    ){
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username).orElseThrow();

        Pageable pageable = PageRequest.of(skip/limit, limit);

        Page<Favorite> favoritePage = favoriteRepository.findByUser(user, pageable);

        List<ProductResponse> favorites = favoritePage.getContent().stream().map(Favorite::getProduct).map(product -> productMapper.toProductResponse(product, true)).toList();

        return new ProductPageResponse(
                favorites,
                favoritePage.getTotalElements(),
                skip,
                limit
        );
    }

    @Transactional
    public boolean removeFavorite(
            String authHeader,
            Long productId
    ) {
        User user = authService.requireCurrentUser(authHeader);

        Product product = productRepository.findById(productId)
                .orElseThrow();

        favoriteRepository.deleteByUserAndProduct(user, product);

        return false;
    }

}
