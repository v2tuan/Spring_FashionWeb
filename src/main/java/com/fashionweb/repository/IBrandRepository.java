package com.fashionweb.repository;

import com.fashionweb.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brandName);
    Optional<Brand> findByEmail(String email);
    Optional<Brand> findByPhone(String phone);

}
