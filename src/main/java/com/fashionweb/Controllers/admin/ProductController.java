package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.Embeddable.SizeId;
import com.fashionweb.dto.request.product.ProductDTO;
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
    private ProductService productService;

    @Autowired
    private CategoryService CategoryService;

    @Autowired
    private BrandService BrandService;

    @Autowired
    private SubcategoryService SubcategoryService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private SubcategoryService subcategoryService;

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
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(simplifiedProduct(products));
    }

    @GetMapping("/productlist")
    String showProductList(Model model) {
        List<ProductListDTO> p = simplifiedProduct(productService.getAllProducts());
        model.addAttribute("subcategories", SubcategoryService.getAll());
        model.addAttribute("products", simplifiedProduct(productService.getAllProducts()));

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

    @PostMapping("/createproduct")
    @ResponseBody
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDto) {
        Product product = new Product();

        Optional<Brand> brnd = brandService.findById(productDto.getBrandId());
        Optional<Subcategory> subcat = subcategoryService.getById(productDto.getSubCateId());
        if (brnd.isPresent() && subcat.isPresent()) {
            product.setProdName(productDto.getProdName());
            product.setDescription(productDto.getDescription());
            product.setRegular(productDto.getRegular());
            product.setPromo(productDto.getPromo());
            product.setStatus(productDto.getStatus());
            product.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
            product.setBrand(brnd.get());
            product.setSubcategory(subcat.get());

            productService.createProduct(product);

            return ResponseEntity.ok("Thêm sản phẩm thành công");
        } else {
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }
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
