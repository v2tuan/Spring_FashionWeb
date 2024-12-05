package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Category;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.ProductDTO;
import com.fashionweb.mapper.ProductMapper;
import com.fashionweb.service.IBrandService;
import com.fashionweb.service.ICategoryService;
import com.fashionweb.service.IProductService;
import com.fashionweb.service.ISubcategoryService;
import com.fashionweb.service.Impl.BrandService;
import com.fashionweb.service.Impl.CategoryService;
import com.fashionweb.service.Impl.ProductService;
import com.fashionweb.service.Impl.SubcategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
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
    private ISubcategoryService iSubcategoryService;

    @Autowired
    private ProductMapper productMapper;

    // Hiển thị danh sách sản phẩm
    @GetMapping("/productlist")
    public String showProductList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("product", new Product());
        return "admin/product_list";
    }


    // Hiển thị form thêm sản phẩm mới
    @GetMapping("/addproduct")
    public String AddProductForm(Model model) {
        List<Category> categories = CategoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("brands", BrandService.getAll());
        model.addAttribute("subcategories", SubcategoryService.getAll());

        model.addAttribute("product", new Product());
        return "admin/addOrEditProduct";
    }

//    // Hiển thị form chỉnh sửa sản phẩm
//    @GetMapping("/editproduct/{id}")
//    public String showEditProductForm(@PathVariable Long id, Model model) {
//        Optional<Product> product = productService.getProduct(id);
//        if (product.isPresent()) {
//            model.addAttribute("product", product.get());
//            return "admin/addOrEditProduct"; // Trả về form chỉnh sửa sản phẩm
//        } else {
//            model.addAttribute("error", "Không tìm thấy sản phẩm!");
//            return "redirect:/admin/productlist";
//        }
//    }

    @PostMapping("/createproduct")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDto) {
        Product product = productMapper.toProduct(productDto);
        productService.createProduct(product);
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

//    @PostMapping("/saveproduct")
//    public String saveProduct(
//            @ModelAttribute("product") @Validated Product product,
//            @RequestParam(value = "images", required = false) List<MultipartFile> images,
//            Model model) {
//
//        productService.updateProduct(product);
//        model.addAttribute("message", "Cập nhật sản phẩm thành công!");
//
//        return "redirect:/admin/productlist";
//    }

    // Xử lý xóa sản phẩm
    @PostMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            model.addAttribute("message", "Xóa sản phẩm thành công!");
        } else {
            model.addAttribute("error", "Xóa sản phẩm thất bại!");
        }
        return "redirect:/admin/productlist"; // Quay lại danh sách sản phẩm
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
