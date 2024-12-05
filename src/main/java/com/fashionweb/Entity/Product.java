package com.fashionweb.Entity;

import com.fashionweb.Enum.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    private String prodName;
    private String description;
    private Double regular;
    private Double promo;

//    @Enumerated(EnumType.STRING)
    private Boolean status;

    private Integer totalQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdImage> prodImages;

    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "brandId", nullable = false)
    private Brand brand;

    // Đảm bảo rằng tên thuộc tính này là `subcategory`
    @ManyToOne
    @JoinColumn(name = "subCatId", nullable = false)
    private Subcategory subcategory;

    // Các quan hệ OneToMany
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Size> sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdReview> productReviews;


//    @ManyToOne
//    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
//    private Brand brand;
//
//    @ManyToOne
//    @JoinColumn(name = "subCatId", referencedColumnName = "subCatId")
//    private Subcategory subcategory;
//
//    @OneToMany(mappedBy = "product")
//    private List<Size> sizes;
}

