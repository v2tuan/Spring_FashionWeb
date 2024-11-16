package com.fashionweb.Controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {
    @GetMapping("/add")
    String add(){
        return "admin/brands/addOrEdit";
    }
}
