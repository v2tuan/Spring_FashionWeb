package com.fashionweb.repository;

import com.fashionweb.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrandBrandId(Long brandId); // Tìm sản phẩm theo brandId
    List<Product> findBySubcategorySubCatId(Long subCatId); // Tìm sản phẩm theo subcategoryId
    List<Product> findByStatus(String status); // Tìm sản phẩm theo trạng thái (ví dụ: "active")
}
