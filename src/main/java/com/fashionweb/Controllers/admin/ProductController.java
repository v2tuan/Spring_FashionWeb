package com.fashionweb.Controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class ProductController {

    @GetMapping("addproduct")
    String addproduct(){
        return "admin/products/addOrEdit";
       //return "admin/test";
    }

    @GetMapping("productlist")
    String add(){
        return "admin/product_list";
    }
}
