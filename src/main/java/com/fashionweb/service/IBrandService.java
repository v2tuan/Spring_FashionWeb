package com.fashionweb.service;

import com.fashionweb.Entity.Brand;
import com.fashionweb.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface IBrandService {

    List<Brand> getAll();
    Optional<Brand> findById(Long id);
    Optional<Brand> findByNameOrEmailOrPhone(String keyword);
    Brand createBrand(Brand brand);
    void updateBrand(Brand brand);
    void deleteBrand(Long id);
    List<Product> getProductsByBrandId(Long id);

    Optional<Brand> findByBrandName(String brandName);
}
