package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products_1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proId")
    private int proId;

    @Column(name = "proName")
    private String proName;

    @Column(name = "description")
    private String description;

    @Column(name = "regularPrice")
    private int regularPrice;

    @Column(name = "promotionalPrice")
    private int promotionalPrice;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand_1 brand;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category_1 category;

    @OneToMany(mappedBy = "products")
    private List<ProdImages_1> images;
}
