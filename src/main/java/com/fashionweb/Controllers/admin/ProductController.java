package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Product;
import com.fashionweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/addproduct")
    String addproduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/addOrEditProduct";
//       return "admin/test";
    }

    @PostMapping("/addproduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.createProduct(product);
        return "redirect:/admin/product_list";
    }


    // Edit a product (GET form)
    @GetMapping("/editproduct/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productService.getProduct(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "admin/addOrEditProduct";
        } else {
            model.addAttribute("Error");
            return "redirect:/admin/addOrEditProduct";
        }
    }

    // Update a product (POST)
    @PostMapping("/editproduct/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        product.setProdId(id); // Ensure the ID is set for updating
        productService.updateProduct(product);
        return "redirect:/admin/product_list";
    }

    // Delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product_list";
    }

    @GetMapping("/productlist")
    String product_list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product_list";
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
