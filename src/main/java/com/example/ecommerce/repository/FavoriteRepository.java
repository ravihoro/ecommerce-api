package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Favorite;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    Page<Favorite> findByUser(User user, Pageable pageable);

    @Query("""
            SELECT f.product.id
            FROM Favorite f
            WHERE f.user = :user
            """)
    Set<Long> findProductIdsByUser(User user);

}
