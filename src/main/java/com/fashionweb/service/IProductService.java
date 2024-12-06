package com.fashionweb.service;

import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.Product2DTO;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> getAllProducts(); // Lấy tất cả sản phẩm
    Optional<Product> getProduct(Long prodId); // Lấy sản phẩm theo id
    <S extends Product> S createProduct(S product); // Tạo mới sản phẩm
    <S extends Product> S updateProduct(S product); // Cập nhật thông tin sản phẩm
    boolean deleteProduct(Long prodId); // Xóa sản phẩm

    List<Product> getProductsByBrand(Long brandId); // Tìm sản phẩm theo brandId
    List<Product> getProductsBySubcategory(Long subCateId); // Tìm sản phẩm theo subcategoryId
    List<Product> getProductsByStatus(Boolean status); // Tìm sản phẩm theo trạng thái
    List<Product> findProductsByCategoryId(Long cateId);

    Product2DTO product2DTO(Product product);

}
