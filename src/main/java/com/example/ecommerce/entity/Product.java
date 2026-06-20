package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    private BigDecimal rating;

    private Integer stock;

    private String brand;

    private String sku;

    private Integer weight;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal depth;

    @Column(name = "warranty_information")
    private String warrantyInformation;

    @Column(name = "shipping_information")
    private String shippingInformation;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "return_policy")
    private String returnPolicy;

    @Column(name = "minimum_order_quantity")
    private Integer minimumOrderQuantity;

    private String barcode;

    @Column(name = "qr_code")
    private String qrCode;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

}