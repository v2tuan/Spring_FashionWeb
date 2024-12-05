package com.fashionweb.repository;

import com.fashionweb.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrandBrandId(Long brandId); // Tìm sản phẩm theo brandId
    List<Product> findBySubcategorySubCateId(Long subCatId); // Tìm sản phẩm theo subcategoryId
    List<Product> findByStatus(Boolean status); // Tìm sản phẩm theo trạng thái (ví dụ: "active")

    List<Product> findAllByBrandBrandId(Long id);
    List<Product> findAllBySubcategoryCategoryCategoryId(Long categoryId);
}
    