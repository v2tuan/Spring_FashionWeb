package com.fashionweb.repository;

import com.fashionweb.Entity.Size;
import com.fashionweb.dto.request.product.SizeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISizeRepository extends JpaRepository<Size, Long> {
    // Tìm tất cả size theo sản phẩm
    List<Size> findByProduct_ProdId(Long prodId);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.SizeDTO(s.id.sizeName, s.quantity)
    FROM Size s
    WHERE s.product.prodId = :prodId""")
    List<SizeDTO> fetchSizeDTOs(@Param("prodId") Long prodId);

}
