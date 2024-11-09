package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    private String prodName;
    private String description;
    private BigDecimal regular;
    private BigDecimal promo;
    private Integer quantityInStock;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProdImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<Size> sizes;

    @OneToMany(mappedBy = "product")
    private List<ProdReview> reviews;
}
