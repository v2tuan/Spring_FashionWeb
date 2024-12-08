package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdImage;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.Product2DTO;
import com.fashionweb.dto.request.product.ProductDetailDTO;
import com.fashionweb.dto.request.product.ProductGridDTO;
import com.fashionweb.dto.request.product.ProductListDTO;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private ProdImageService prodImageService;

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

    @Override
    public List<Product> findProductByProdName(String name) {
        return List.of();
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

        Double avgRating = prodReviewService.averageRatingByProdId(product.getProdId());

        return new ProductGridDTO(
                product.getProdId(),
                product.getProdName(),
                product.getRegular(),
                product.getPromo(),
                (product.getCreateDate()),
                isSale,
                percent,
                avgRating >= 86,
                product.getBrand().getBrandId(),
                product.getSubcategory().getSubCateId(),
                this.getImgName(product.getImages()),
                avgRating,
                prodReviewService.reviewCountByProdId(product.getProdId())
        );
    }

//    public List<ProductGridDTO> productGridDTOs(List<Product> products) {
//        return products.stream().filter(product -> Boolean.TRUE.equals(product.getStatus())).map(this::productGridDTO).toList();
//    }

    public List<ProductGridDTO> findAllProductGrid(boolean status) {
        List<ProductGridDTO> products = iProductRepository.fetchProductGrid(status);

        products.forEach(product -> product.setIsBest(product.getRating() >= 86));

        return products;
    }

    public Page<ProductGridDTO> findAllProductGridPageable(boolean status, Pageable pageable) {
        return iProductRepository.fetchProductGridPageable(status, pageable);
    }

    public Page<ProductGridDTO> findAllProductGridCriteriaPageable(Long subCateId, boolean status, Pageable pageable) {
        Page<ProductGridDTO> products = iProductRepository.fetchProductGridPageableByCriteria(subCateId, status, pageable);

        products.getContent().forEach(product -> product.setIsBest(product.getRating() >= 86));

        return products;
    }

    public Page<ProductGridDTO> searchProductGridCriteriaPageable(String keyword, Long subCateId, boolean status, Pageable pageable) {
        Page<ProductGridDTO> products = iProductRepository.searchProductGridPageable(keyword, subCateId, status, pageable);

        products.getContent().forEach(product -> product.setIsBest(product.getRating() >= 86));

        return products;
    }

    public List<ProductListDTO> findAllProductList() {
        return iProductRepository.fetchProductList();
    }

    public Page<ProductListDTO> findAllProductListPageable(Pageable pageable) {
        return iProductRepository.fetchProductListPageable(pageable);
    }

    public Page<ProductListDTO> findAllProductListCriteriaPageable(Long subCateId, Boolean status, Pageable pageable) {
        return iProductRepository.fetchProductListByCriteria(subCateId, status, pageable);
    }

    public Optional<ProductDetailDTO> findProductDetailByProdId(Long prodId) {
        Optional<ProductDetailDTO> productDetailDTO = iProductRepository.fetchProductDetailById(prodId);
        if (productDetailDTO.isPresent()) {
            productDetailDTO.get().setImgURL(prodImageService.findImageNamesByProdId(prodId));
            productDetailDTO.get().setSizeDTOs(sizeService.findSizeDTOsByProdId(prodId));
            return productDetailDTO;
        }

        return Optional.empty();
    }

    public Optional<Product2DTO> findProduct2DTOByProdId(Long prodId) {
        Optional<Product2DTO> product2DTO = iProductRepository.fetchProduct2DTOById(prodId);
        if (product2DTO.isPresent()) {
            product2DTO.get().setImgURLs(prodImageService.findImageNamesByProdId(prodId));
            product2DTO.get().setSizeDTOs(sizeService.findSizeDTOsByProdId(prodId));
            return product2DTO;
        }

        return Optional.empty();
    }

    public boolean disableProduct(Long prodId) {
        try {
            Product product = this.getProduct(prodId).get();
            product.setStatus(false);
            this.updateProduct(product);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
