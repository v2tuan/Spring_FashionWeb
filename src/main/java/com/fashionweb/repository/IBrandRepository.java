package com.fashionweb.repository;

import com.fashionweb.Entity.Brand;
import com.fashionweb.dto.request.BrandDTO;
import com.fashionweb.dto.request.brand.BrandDTO2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brandName);
    Optional<Brand> findByEmail(String email);
    Optional<Brand> findByPhone(String phone);

    @Query("""
    SELECT new com.fashionweb.dto.request.BrandDTO(
        b.brandId,
        b.brandName) FROM Brand b""")
    List<BrandDTO> fetchBrandDTOs();

    @Query("""
    SELECT new com.fashionweb.dto.request.brand.BrandDTO2(
        b.brandId,
        b.brandName,
        b.images,
        (SELECT COUNT(p.prodId) FROM Product p WHERE p.brand.brandId = b.brandId)
    ) FROM Brand b""")
    List<BrandDTO2> fetchBrandDTO2s();
}
