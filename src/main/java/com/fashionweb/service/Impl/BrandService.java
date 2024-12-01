package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Brand;
import com.fashionweb.repository.IBrandRepository;
import com.fashionweb.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private IBrandRepository brandRepos;

    @Override
    public List<Brand> getAll() {
        return brandRepos.findAll();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepos.findById(id);
    }

    @Override
    public Optional<Brand> findByNameOrEmailOrPhone(String keyword) {
        Optional<Brand> brand = brandRepos.findByBrandName(keyword);

        if (brand.isEmpty()) {
            brand = brandRepos.findByEmail(keyword);

            if (brand.isEmpty()) {
                brand = brandRepos.findByPhone(keyword);

                if (brand.isEmpty()) {
                    return Optional.empty();
                }
            }
        }

        return brand;
    }

    @Override
    public void createBrand(Brand brand) {
        brandRepos.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        if (!brandRepos.existsById(brand.getBrandId())) {
            throw new RuntimeException("Không tìm thấy 'Brand' với id(" + brand.getBrandId() + ")");
        }

        brandRepos.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepos.existsById(id)) {
            throw new RuntimeException("Không tìm thấy 'Brand' với id(" + id + ")");
        }

        brandRepos.deleteById(id);
    }

}
