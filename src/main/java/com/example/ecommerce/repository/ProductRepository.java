package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {
            "category",
    })
    Optional<Product> findDetailById(Long id);

    Page<Product> findByCategorySlug(String slug, Pageable pageable);

    @Query(value = """
        SELECT *
        FROM products
        WHERE title ILIKE CONCAT('%', :query, '%')
        OR brand ILIKE CONCAT('%', :query, '%')
        ORDER BY GREATEST(
            similarity(title,:query),
            similarity(brand, :query)
        ) DESC
    """, countQuery = """
        SELECT COUNT(*)
            FROM products
                WHERE title ILIKE CONCAT('%', :query, '%')
                    OR brand ILIKE CONCAT('%',:query, '%')

    """, nativeQuery = true)
    Page<Product> searchProducts(
            @Param("query") String query,
            Pageable pageable
    );

}
