package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProdReviews")
public class ProdReview {
    @EmbeddedId
    private ProdReviewsId reviewId;

    private Integer rating;
    private String comment;
    private LocalDate createDate;
    private String images;

    @ManyToOne
    @MapsId("prodId") // Maps "prodId" from SizeId
    @JoinColumn(name = "prodId", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("accId") // Maps "prodId" from SizeId
    @JoinColumn(name = "accId", nullable = false)
    private Account account;
}
