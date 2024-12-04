package com.fashionweb.service;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdReview;

import java.util.List;
import java.util.Optional;

public interface IProdReviewService {
    List<ProdReview> getAllProdReviews();
    Optional<ProdReview> getProdReview(Long reviewId);
    <S extends ProdReview> S createProdReview(S prodReview);
    <S extends ProdReview> S updateProdReview(S prodReview);
    void deleteProdReview(Long reviewId);

    List<ProdReview> getReviewsByProduct(Long prodId);
    List<ProdReview> findById(ProdReviewsId id);
}
