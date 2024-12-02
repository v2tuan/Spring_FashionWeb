package com.fashionweb.repository;

import com.fashionweb.Entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISizeRepository extends JpaRepository<Size, Long> {
    // Tìm tất cả size theo sản phẩm
    List<Size> findByProduct_ProdId(Long prodId);
}
