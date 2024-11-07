package com.fashionweb.Controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @GetMapping("add")
    String add(){
        return "admin/products/addOrEdit";
//        return "admin/test";
    }
}
