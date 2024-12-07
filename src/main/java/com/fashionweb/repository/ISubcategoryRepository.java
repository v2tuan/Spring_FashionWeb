package com.fashionweb.repository;

import com.fashionweb.Entity.Subcategory;
import com.fashionweb.dto.request.category.SubcategoryListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findBySubCateName(String name);

    @Query("SELECT s FROM Subcategory s WHERE s.category.categoryId = :id")
    List<Subcategory> findAllByCategoryCategoryId(@Param("id") Long id);

    @Query("""
    SELECT new com.fashionweb.dto.request.category.SubcategoryListDTO(
        s.subCateId,
        s.subCateName
    )
    FROM Subcategory s""")
    List<SubcategoryListDTO> fetchSubcategoryListByCatId();

    @Query("""
    SELECT new com.fashionweb.dto.request.category.SubcategoryListDTO(
        s.subCateId,
        s.subCateName
    )
    FROM Subcategory s WHERE s.category.categoryId = :catId""")
    List<SubcategoryListDTO> fetchSubcategoryList(@Param("catId") Long catId);
}
