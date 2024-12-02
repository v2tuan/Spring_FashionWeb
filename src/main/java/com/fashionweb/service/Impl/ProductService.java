package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Product;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public List<Product> getAllProducts() {
        // Trả về tất cả sản phẩm từ cơ sở dữ liệu
        return iProductRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long prodId) {
        // Trả về sản phẩm theo ID
        return iProductRepository.findById(prodId);
    }

    @Override
    public <S extends Product> S createProduct(S product) {
        // Kiểm tra tính hợp lệ của sản phẩm trước khi lưu
        if (product.getBrand() == null || product.getSubcategory() == null) {
            throw new RuntimeException("Thông tin sản phẩm không hợp lệ.");
        }
        return iProductRepository.save(product);
    }

    @Override
    public <S extends Product> S updateProduct(S product) {
        // Kiểm tra tính hợp lệ của sản phẩm trước khi cập nhật
        if (product.getBrand() == null || product.getSubcategory() == null) {
            throw new RuntimeException("Thông tin sản phẩm không hợp lệ.");
        }
        return iProductRepository.save(product);
    }

    @Override
    public void deleteProduct(Long prodId) {
        // Xóa sản phẩm theo ID
        iProductRepository.deleteById(prodId);
    }

    @Override
    public List<Product> getProductsByBrand(Long brandId) {
        // Tìm sản phẩm theo brandId
        return iProductRepository.findByBrandBrandId(brandId);
    }

    @Override
    public List<Product> getProductsBySubcategory(Long subCatId) {
        // Tìm sản phẩm theo subcategoryId
        return iProductRepository.findBySubcategorySubCatId(subCatId);
    }

    @Override
    public List<Product> getProductsByStatus(String status) {
        // Tìm sản phẩm theo trạng thái
        return iProductRepository.findByStatus(status);
    }
}
