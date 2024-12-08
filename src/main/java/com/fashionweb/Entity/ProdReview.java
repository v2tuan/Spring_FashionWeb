package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @JoinColumn(name = "prodId", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "accId", insertable = false, updatable = false)
    private Account account;
}
