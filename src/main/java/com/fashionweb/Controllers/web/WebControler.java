package com.fashionweb.Controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class WebControler {
    @GetMapping
    String home() {
        return "web/home";
    }

    @GetMapping("/shop")
    String shop() {
        return "web/shop/shop_content";
    }

    @GetMapping("/productdetail")
    String productdetail() {
        return "web/shop/product_details";
    }
}
