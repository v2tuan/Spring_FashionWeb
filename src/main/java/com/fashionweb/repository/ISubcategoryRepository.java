package com.fashionweb.repository;

import com.fashionweb.Entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findBySubCateName(String name);
    List<Subcategory> findAllByCategoryCategoryId(Long id);

}
