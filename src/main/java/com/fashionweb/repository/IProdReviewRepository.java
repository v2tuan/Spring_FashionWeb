package com.fashionweb.repository;

import com.fashionweb.Entity.ProdReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdReviewRepository extends JpaRepository<ProdReview, Long> {
    List<ProdReview> findByProductProdId(Long prodId); // Tìm các review theo sản phẩm
    List<ProdReview> findByCustomerCustId(Long custId); // Tìm các review theo khách hàng
    List<ProdReview> findByProductProdIdAndSizeSizeId(Long prodId, Long sizeId); // Tìm các review theo sản phẩm và size
}
