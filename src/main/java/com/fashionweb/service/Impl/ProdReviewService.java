package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.repository.IProdReviewRepository;
import com.fashionweb.service.IProdReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdReviewService implements IProdReviewService {

    @Autowired
    private IProdReviewRepository iProdReviewRepository;

    @Override
    public List<ProdReview> getAllProdReviews() {
        // Trả về tất cả đánh giá sản phẩm từ cơ sở dữ liệu
        return iProdReviewRepository.findAll();
    }

    @Override
    public Optional<ProdReview> getProdReview(Long reviewId) {
        // Trả về đánh giá sản phẩm theo ID, nếu không tìm thấy sẽ trả về Optional.empty()
        return iProdReviewRepository.findById(reviewId);
    }

    @Override
    public <S extends ProdReview> S createProdReview(S prodReview) {
        // Kiểm tra tính hợp lệ của thông tin sản phẩm và khách hàng trước khi lưu
        if (prodReview.getProduct() == null ) {
            throw new RuntimeException("Thông tin sản phẩm hoặc khách hàng không hợp lệ.");
        }
        return iProdReviewRepository.save(prodReview);
    }

    @Override
    public <S extends ProdReview> S updateProdReview(S prodReview) {
        // Kiểm tra tính hợp lệ của thông tin trước khi cập nhật
        if (prodReview.getProduct() == null) {
            throw new RuntimeException("Thông tin sản phẩm hoặc khách hàng không hợp lệ.");
        }
        return iProdReviewRepository.save(prodReview);
    }

    @Override
    public void deleteProdReview(Long reviewId) {
        // Xóa đánh giá sản phẩm theo ID
        iProdReviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ProdReview> getReviewsByProduct(Long prodId) {
        // Tìm các đánh giá sản phẩm theo prodId
        return iProdReviewRepository.findByProductProdId(prodId);
    }


    @Override
    public List<ProdReview> findById(ProdReviewsId id) {
        return iProdReviewRepository.findByReviewId(id);
    }

    public Double averageRatingByProdId(Long prodId) {
        Double averageRating = iProdReviewRepository.averageRatingByProdId(prodId);
        return (averageRating == null) ? 0.0 : averageRating;
    }

    public Long reviewCountByProdId(Long prodId) {
        return iProdReviewRepository.reviewCountByProdId(prodId);
    }
}
