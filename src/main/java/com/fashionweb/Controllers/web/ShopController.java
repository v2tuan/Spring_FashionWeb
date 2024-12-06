package com.fashionweb.Controllers.web;

import com.fashionweb.dto.request.category.CategoryGridDTO;
import com.fashionweb.service.Impl.CategoryService;
import com.fashionweb.service.Impl.ProductService;
import com.fashionweb.service.Impl.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/home/shop")
public class ShopController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/categories")
    @ResponseBody
    ResponseEntity<?> getCategories() {
        List<CategoryGridDTO> categoryGridDTOs = categoryService.categoryGridDTOs(categoryService.findAll());
        return ResponseEntity.ok(categoryGridDTOs);
    }

    @GetMapping("/subcategories")
    @ResponseBody
    ResponseEntity<?> getSubcategories() {
        List<CategoryGridDTO> categoryGridDTOs = categoryService.categoryGridDTOs(categoryService.findAll());
        return ResponseEntity.ok(categoryGridDTOs);
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts() {
        List<CategoryGridDTO> categoryGridDTOs = categoryService.categoryGridDTOs(categoryService.findAll());
        return ResponseEntity.ok(categoryGridDTOs);
    }

    @GetMapping
    String shop(Model model) {

        return "web/shop/shop_content";
    }

}
