package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProdReviews")
public class ProdReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Integer rating;
    private String comment;
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "custId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "prodId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sizeId")
    private Size size;
}
