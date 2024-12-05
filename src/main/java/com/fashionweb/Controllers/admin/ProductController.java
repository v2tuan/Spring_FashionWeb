package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.dto.request.product.ProductDTO;
import com.fashionweb.mapper.ProductMapper;
import com.fashionweb.service.Impl.BrandService;
import com.fashionweb.service.Impl.CategoryService;
import com.fashionweb.service.Impl.ProductService;
import com.fashionweb.service.Impl.SubcategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService CategoryService;

    @Autowired
    private BrandService BrandService;

    @Autowired
    private SubcategoryService SubcategoryService;

    @Autowired
    private ProductMapper productMapper;

    public List<Map<String, Object>> simplifiedImages(List<ProdImage> images) {
        return images.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("imgId", item.getProductImageId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedSizes(List<Size> sizes) {
        return sizes.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("sizeId", item.getId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedReviews(List<ProdReview> reviews) {
        return reviews.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("reviewId", item.getReviewId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedProduct(List<Product> products) {
        return products.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("id", item.getProdId());
            items.put("name", item.getProdName());
            items.put("description", item.getDescription());
            items.put("regular", item.getRegular());
            items.put("promo", item.getPromo());
            items.put("status", item.getStatus());
            items.put("totalQuantity", item.getTotalQuantity());
            items.put("imgIds", simplifiedImages(item.getImages()));
            items.put("createDate", item.getCreateDate());
            items.put("brandId", item.getBrand().getBrandId());
            items.put("subCatId", item.getSubcategory().getSubCateId());
            items.put("sizeIds", simplifiedSizes(item.getSizes()));
            items.put("reviewIds", simplifiedReviews(item.getProductReviews()));
            return items;
        }).toList();
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts(){
        List<Product> products = productService.getAllProducts();

        List<Map<String, Object>> validatedProducts = simplifiedProduct(products);

        return ResponseEntity.ok(validatedProducts);
    }

    @GetMapping("/productlist")
    String showProductList(Model model) {
        model.addAttribute("subcategories", SubcategoryService.getAll());

        return "admin/product_list";
    }

    @GetMapping("/addproduct")
    public String AddProductForm(Model model) {
        List<Category> categories = CategoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("brands", BrandService.getAll());
        model.addAttribute("subcategories", SubcategoryService.getAll());

        model.addAttribute("product", new Product());
        return "admin/addOrEditProduct";
    }

//    @PostMapping("/createproduct")
//    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDto) {
////        Product product = productMapper.toProduct(productDto);
////        productService.createProduct(product);
////        return ResponseEntity.ok("Thêm sản phẩm thành công");
//    }

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
    String order_list(){
        return "admin/order_list";
    }

    @GetMapping("/reviews")
    String reviews() { return "admin/reviews"; }

    @GetMapping("/profile-settings")
    String profileSettings() { return "admin/profile_settings"; }

    @GetMapping("/order-detail")
    String orderDetail() {
        return "admin/order_detail";
    }
}
