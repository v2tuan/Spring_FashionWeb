package com.fashionweb.repository;

import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.Product2DTO;
import com.fashionweb.dto.request.product.ProductDetailDTO;
import com.fashionweb.dto.request.product.ProductGridDTO;
import com.fashionweb.dto.request.product.ProductListDTO;
import com.fashionweb.dto.response.ProductResponeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByProdName (String name);
    List<Product> findByBrandBrandId(Long brandId); // Tìm sản phẩm theo brandId
    List<Product> findBySubcategorySubCateId(Long subCatId); // Tìm sản phẩm theo subcategoryId
    List<Product> findByStatus(Boolean status); // Tìm sản phẩm theo trạng thái (ví dụ: "active")

    List<Product> findAllByBrandBrandId(Long id);
    List<Product> findAllBySubcategoryCategoryCategoryId(Long categoryId);

    @Query("""
        SELECT new com.fashionweb.dto.response.ProductResponeDTO(
            p.prodName, 
            p.regular, 
            p.promo, 
            (SELECT pi.imgURL 
             FROM ProdImage pi 
             WHERE pi.product.prodId = p.prodId 
             ORDER BY pi.productImageId.stt ASC 
             LIMIT 1)
        )
        FROM Product p 
        JOIN p.sizes s 
        JOIN OrderItem oi ON s.id.sizeName = oi.size.id.sizeName AND s.id.prodId = oi.size.id.prodId
        GROUP BY p.prodId, p.prodName, p.regular, p.promo
        HAVING SUM(oi.quantity) > 10
        ORDER BY SUM(oi.quantity) DESC
        """)
    List<ProductResponeDTO> findTopSellingProductSummaries();


    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductGridDTO(
        p.prodId,
        p.prodName,
        p.regular,
        p.promo,
        p.createDate,
        (CASE WHEN p.promo < p.regular THEN TRUE ELSE FALSE END),
        (CASE WHEN p.promo < p.regular THEN CAST(100 * (1 - p.promo / p.regular) AS integer) ELSE 0 END),
        null,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1),
        (SELECT COALESCE(AVG(pr.rating), 0) FROM ProdReview pr WHERE pr.product.prodId = p.prodId),
        (SELECT COUNT(pr) FROM ProdReview pr WHERE pr.product.prodId = p.prodId)
    )
    FROM Product p""")
    List<ProductGridDTO> fetchProductGrid(@Param("status") boolean status);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductGridDTO(
        p.prodId,
        p.prodName,
        p.regular,
        p.promo,
        p.createDate,
        (CASE WHEN p.promo < p.regular THEN TRUE ELSE FALSE END),
        (CASE WHEN p.promo < p.regular THEN CAST(100 * (1 - p.promo / p.regular) AS integer) ELSE 0 END),
        null,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1),
        (SELECT COALESCE(AVG(pr.rating), 0) FROM ProdReview pr WHERE pr.product.prodId = p.prodId),
        (SELECT COUNT(pr) FROM ProdReview pr WHERE pr.product.prodId = p.prodId)
    )
    FROM Product p WHERE ( :status IS NULL OR p.status = :status )""")
    Page<ProductGridDTO> fetchProductGridPageable(@Param("status") boolean status, Pageable pageable);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductGridDTO(
        p.prodId,
        p.prodName,
        COALESCE(p.regular, 0),
        COALESCE(p.promo, 0),
        p.createDate,
        (CASE WHEN COALESCE(p.promo, 0) < COALESCE(p.regular, 0) THEN TRUE ELSE FALSE END),
        (CASE WHEN COALESCE(p.promo, 0) < COALESCE(p.regular, 0) THEN CAST(100 * (1 - COALESCE(p.promo, 0) / p.regular) AS integer) ELSE 0 END),
        false,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1),
        (SELECT COALESCE(AVG(pr.rating), 0) FROM ProdReview pr WHERE pr.product.prodId = p.prodId),
        (SELECT COUNT(pr) FROM ProdReview pr WHERE pr.product.prodId = p.prodId)
    )
    FROM Product p WHERE
        ( :status IS NULL OR p.status = :status ) AND
        ( :subCateId IS NULL OR p.subcategory.subCateId = :subCateId )""")
    Page<ProductGridDTO> fetchProductGridPageableByCriteria(@Param("subCateId") Long subCateId,
                                                            @Param("status") boolean status,
                                                            Pageable pageable);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductListDTO(
        p.prodId,
        p.prodName,
        p.description,
        p.regular,
        p.promo,
        p.status,
        p.createDate,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1)
    )
    FROM Product p""")
    List<ProductListDTO> fetchProductList();

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductListDTO(
        p.prodId,
        p.prodName,
        p.description,
        p.regular,
        p.promo,
        p.status,
        p.createDate,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1)
    )
    FROM Product p""")
    Page<ProductListDTO> fetchProductListPageable(Pageable pageable);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductListDTO(
        p.prodId,
        p.prodName,
        p.description,
        p.regular,
        p.promo,
        p.status,
        p.createDate,
        p.brand.brandId,
        p.subcategory.subCateId,
        (SELECT pi.imgURL FROM ProdImage pi WHERE pi.product.prodId = p.prodId ORDER BY pi.productImageId.stt ASC LIMIT 1)
    )
    FROM Product p WHERE
        ( :subcategoryId IS NULL OR p.subcategory.subCateId = :subcategoryId ) AND
        ( :status IS NULL OR p.status = :status )""")
    Page<ProductListDTO> fetchProductListByCriteria(@Param("subcategoryId") Long subCateId,
                                                    @Param("status") Boolean status,
                                                    Pageable pageable);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.ProductDetailDTO(
        p.prodId,
        p.prodName,
        p.description,
        p.regular,
        p.promo,
        p.createDate,
        CASE WHEN p.promo < p.regular THEN true ELSE false END,
        CASE WHEN p.promo < p.regular THEN CAST(100 * (1 - p.promo / p.regular) AS integer) ELSE 0 END,
        p.brand.brandName,
        p.subcategory.subCateName,
        (SELECT COALESCE(AVG(pr.rating), 0) FROM ProdReview pr WHERE pr.product.prodId = p.prodId),
        (SELECT COUNT(pr) FROM ProdReview pr WHERE pr.product.prodId = p.prodId),
        null,
        null
    )
    FROM Product p WHERE p.prodId = :prodId""")
    Optional<ProductDetailDTO> fetchProductDetailById(@Param("prodId") Long prodId);

    @Query("""
    SELECT new com.fashionweb.dto.request.product.Product2DTO(
        p.prodId,
        p.prodName,
        p.description,
        p.regular,
        p.promo,
        p.status,
        p.brand.brandId,
        p.subcategory.subCateId,
        null,
        null
    )
    FROM Product p WHERE p.prodId = :prodId""")
    Optional<Product2DTO> fetchProduct2DTOById(@Param("prodId") Long prodId);

    //void searchProduct();
}
    