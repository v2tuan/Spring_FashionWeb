package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Brand;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.BrandDTO;
import com.fashionweb.dto.request.brand.BrandDTO2;
import com.fashionweb.repository.IBrandRepository;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private IBrandRepository brandRepos;
    @Autowired
    private IProductRepository productRepos;

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
        return brandRepos.findByBrandName(keyword).or(() -> brandRepos.findByEmail(keyword).or(() -> brandRepos.findByPhone(keyword)));
    }

    @Override
    public Brand createBrand(Brand brand) {
        brandRepos.save(brand);
        return brand;
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

    @Override
    public List<Product> getProductsByBrandId(Long id) {
        return productRepos.findAllByBrandBrandId(id);
    }

    @Override
    public Optional<Brand> findByBrandName(String brandName) {
        return brandRepos.findByBrandName(brandName);

    }

    public List<BrandDTO> getBrandDTOs() {
        return brandRepos.fetchBrandDTOs();
    }

    public List<BrandDTO2> getBrandDTO2s() {
        return brandRepos.fetchBrandDTO2s();
    }
}
