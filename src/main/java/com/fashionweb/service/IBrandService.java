package com.fashionweb.service;

import com.fashionweb.Entity.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandService {

    List<Brand> getAll();
    Optional<Brand> findById(Long id);
    Optional<Brand> findByNameOrEmailOrPhone(String keyword);
    void createBrand(Brand brand);
    void updateBrand(Brand brand);
    void deleteBrand(Long id);

}
