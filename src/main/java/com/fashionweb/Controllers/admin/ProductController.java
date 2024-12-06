package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.Embeddable.SizeId;
import com.fashionweb.dto.request.product.Product2DTO;
import com.fashionweb.dto.request.product.ProductListDTO;
import com.fashionweb.service.Impl.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;
    @Autowired
    SubcategoryService subcategoryService;

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

    public List<SizeId> simplifiedSizes(List<Size> sizes) {
        return sizes.stream().map(Size::getId).toList();
    }

    public List<ProdReviewsId> simplifiedReviews(List<ProdReview> reviews) {
        return reviews.stream().map(ProdReview::getReviewId).toList();
    }

    public List<ProductListDTO> simplifiedProduct(List<Product> products) {
        return products.stream().map(product -> {
            ProductListDTO productListDTO = new ProductListDTO();
            productListDTO.setProdId(product.getProdId());
            productListDTO.setProdName(product.getProdName());
            productListDTO.setDescription(product.getDescription());
            productListDTO.setRegular(product.getRegular());
            productListDTO.setPromo(product.getPromo());
            productListDTO.setStatus(product.getStatus() != null && product.getStatus());
            productListDTO.setCreateDate(product.getCreateDate());
            productListDTO.setImgURL(getImgName(product.getImages()));
            productListDTO.setBrandId(product.getBrand().getBrandId());
            productListDTO.setSubCateId(product.getSubcategory().getSubCateId());
            return productListDTO;
        }).toList();
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts() {
        List<Product> Products = productService.getAllProducts();
        List<ProductListDTO> ProductLists = simplifiedProduct(Products);

        return ResponseEntity.ok(ProductLists);
    }

    @GetMapping("/productlist")
    String showProductList(Model model) {
        List<Subcategory> Subcategories = subcategoryService.getAll();
        List<Product> Products = productService.getAllProducts();

        List<ProductListDTO> ProductLists = simplifiedProduct(Products);
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("products", ProductLists);

        return "admin/product_list";
    }

    @GetMapping("/addproduct")
    public String AddProductForm(Model model) {
        List<Brand> Brands = brandService.getAll();
        List<Subcategory> Subcategories = subcategoryService.getAll();

        model.addAttribute("brands", Brands);
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("product", new Product());

        return "admin/addOrEditProduct";
    }

    @PostMapping("/createproduct")
    @ResponseBody
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product2DTO product2Dto) {
        Product product = new Product();

        Optional<Brand> brnd = brandService.findById(product2Dto.getBrandId());
        Optional<Subcategory> subcat = subcategoryService.getById(product2Dto.getSubCateId());
        if (brnd.isPresent() && subcat.isPresent()) {
            product.setProdName(product2Dto.getProdName());
            product.setDescription(product2Dto.getDescription());
            product.setRegular(product2Dto.getRegular());
            product.setPromo(product2Dto.getPromo());
            product.setStatus(product2Dto.getStatus());
            product.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
            product.setBrand(brnd.get());
            product.setSubcategory(subcat.get());

            productService.createProduct(product);

            return ResponseEntity.ok("Thêm sản phẩm thành công");
        } else {
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }
    }

    @GetMapping("/editproduct/{prodId}")
    public String EditProductForm(@PathVariable("prodId") Long prodId, Model model) {
        List<Brand> Brands = brandService.getAll();
        List<Subcategory> Subcategories = subcategoryService.getAll();

        model.addAttribute("brands", Brands);
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("product", new Product());
        model.addAttribute("prodId", prodId);

        return "admin/editProduct";
    }

    @PostMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            model.addAttribute("message", "Đã xóa sản phẩm");
        } else {
            model.addAttribute("error", "Xóa sản phẩm thất bại");
        }
        return "redirect:/admin/productlist";
    }

    @GetMapping("/orderlist")
    String order_list() {
        return "admin/order_list";
    }

    @GetMapping("/reviews")
    String reviews() {
        return "admin/reviews";
    }

    @GetMapping("/profile-settings")
    String profileSettings() {
        return "admin/profile_settings";
    }

    @GetMapping("/order-detail")
    String orderDetail() {
        return "admin/order_detail";
    }
}
