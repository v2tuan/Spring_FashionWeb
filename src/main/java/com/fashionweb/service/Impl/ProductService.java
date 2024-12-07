package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdImage;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.Product2DTO;
import com.fashionweb.dto.request.product.ProductGridDTO;
import com.fashionweb.dto.request.product.ProductListDTO;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ProdReviewService prodReviewService;

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
    public boolean deleteProduct(Long prodId) {
        // Xóa sản phẩm theo ID
        iProductRepository.deleteById(prodId);
        return true;
    }

    @Override
    public List<Product> getProductsByBrand(Long brandId) {
        // Tìm sản phẩm theo brandId
        return iProductRepository.findByBrandBrandId(brandId);
    }

    @Override
    public List<Product> getProductsBySubcategory(Long subCatId) {
        // Tìm sản phẩm theo subcategoryId
        return iProductRepository.findBySubcategorySubCateId(subCatId);
    }

    @Override
    public List<Product> getProductsByStatus(Boolean status) {
        // Tìm sản phẩm theo trạng thái
        return iProductRepository.findByStatus(status);
    }

    @Override
    public List<Product> findProductsByCategoryId(Long cateId) {
        return iProductRepository.findAllBySubcategoryCategoryCategoryId(cateId);
    }

    public String getImgName(List<ProdImage> images) {
        if (images == null || images.isEmpty()) {
            return "default";
        } else if (images.getFirst().getImgURL() == null) {
            return "default";
        }

        return images.getFirst().getImgURL();
    }

    public List<String> simplifiedImages(List<ProdImage> images) {
        if (images == null || images.isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add("default");
            return list;
        }

        return images.stream().map(img -> {
            if (img == null || img.getImgURL() == null) return "default";
            else return img.getImgURL();
        }).toList();
    }

    public List<ProdReviewsId> simplifiedReviews(List<ProdReview> reviews) {
        return reviews.stream().map(ProdReview::getReviewId).toList();
    }

    @Override
    public Product2DTO product2DTO(Product product) {
        Product2DTO product2DTO = new Product2DTO();
        product2DTO.setProdId(product.getProdId());
        product2DTO.setProdName(product.getProdName());
        product2DTO.setDescription(product.getDescription());
        product2DTO.setRegular(product.getRegular());
        product2DTO.setPromo(product.getPromo());
        product2DTO.setStatus(product.getStatus());
        product2DTO.setBrandId(product.getBrand().getBrandId());
        product2DTO.setSubCateId(product.getSubcategory().getSubCateId());
        product2DTO.setImgURLs(simplifiedImages(product.getImages()));
        product2DTO.setSizeDTOs(sizeService.sizeDTOs(product.getSizes()));
        return product2DTO;
    }

    public ProductGridDTO productGridDTO(Product product) {
        boolean isSale;
        int percent;
        if (product.getPromo() == null || product.getPromo().equals(product.getRegular())) {
            isSale = false;
            percent = 0;
        }
        else {
            isSale = true;
            percent = (int) ((1 - product.getPromo()/product.getRegular())*100);
        }

        return new ProductGridDTO(
                product.getProdId(),
                product.getProdName(),
                product.getRegular(),
                product.getPromo(),
                (product.getCreateDate()),
                isSale,
                percent,
                product.getBrand().getBrandId(),
                product.getSubcategory().getSubCateId(),
                this.getImgName(product.getImages()),
                prodReviewService.averageRatingByProdId(product.getProdId()),
                prodReviewService.reviewCountByProdId(product.getProdId())
        );
    }

    public List<ProductGridDTO> productGridDTOs(List<Product> products) {
        return products.stream().filter(product -> Boolean.TRUE.equals(product.getStatus())).map(this::productGridDTO).toList();
    }

    public List<ProductGridDTO> findAllProductGrid(boolean status) {
        return iProductRepository.fetchProductGrid(status);
    }

    public List<ProductListDTO> findAllProductList() {
        return iProductRepository.fetchProductList();
    }
}
