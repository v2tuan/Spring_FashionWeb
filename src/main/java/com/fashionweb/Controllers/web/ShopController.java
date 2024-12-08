package com.fashionweb.Controllers.web;

import com.fashionweb.dto.request.category.CategoryGridDTO;
import com.fashionweb.dto.request.product.ProductGridDTO;
import com.fashionweb.service.Impl.CategoryService;
import com.fashionweb.service.Impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home/shop")
public class ShopController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/categories")
    @ResponseBody
    ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.categoryGridDTOs(categoryService.findAll()));
    }

    @GetMapping("/subcategories")
    @ResponseBody
    ResponseEntity<?> getSubcategories() {
        return ResponseEntity.ok(categoryService.categoryGridDTOs(categoryService.findAll()));
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productService.findAllProductGrid(true));
    }

    @GetMapping("/productDetail")
    @ResponseBody
    ResponseEntity<?> getProductDetail(@RequestBody @Valid Long prodId) {
        return ResponseEntity.ok(productService.findProductDetailByProdId(prodId));
    }

    @GetMapping
    String shop(Model model) {
        List<CategoryGridDTO> categoryGridDTOs = categoryService.categoryGridDTOs(categoryService.findAll());
        List<ProductGridDTO> productGridDTOs = productService.findAllProductGrid(true);

        model.addAttribute("categories", categoryGridDTOs);
        model.addAttribute("products", productGridDTOs);

        return "web/shop/shop_content";
    }

    @GetMapping("/product-detail/id={prodId}")
    String productDetail(@PathVariable Long prodId, Model model) {
        model.addAttribute("product", productService.findProductDetailByProdId(prodId).get());
        return "web/shop/product_detail";
    }

}
