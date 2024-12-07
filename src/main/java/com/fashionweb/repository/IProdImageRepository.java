package com.fashionweb.repository;

import com.fashionweb.Entity.ProdImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdImageRepository extends JpaRepository<ProdImage, Long> {
    List<ProdImage> findByProductProdId(Long prodId);  // Tìm các hình ảnh liên quan đến sản phẩm
    @Query("""
    SELECT pi.imgURL
    FROM ProdImage pi
    WHERE pi.product.prodId = :prodId
    ORDER BY pi.productImageId.stt ASC""")
    List<String> fetchImageNamesByProdId(@Param("prodId") Long prodId);
}
