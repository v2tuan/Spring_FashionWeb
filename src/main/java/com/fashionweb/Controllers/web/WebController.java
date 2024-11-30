package com.fashionweb.Controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class WebController {
    @GetMapping
    String home() {
        return "web/home";
    }

    @GetMapping("/shop")
    String shop() {
        return "web/shop/shop_content";
    }

    @GetMapping("/product-detail")
    String productDetail() {
        return "web/shop/product_detail";
    }

    @GetMapping("/order-detail")
    String orderDetail() {
        return "web/shop/order_detail";
    }

    @GetMapping("/check-out")
    String checkOut() {
        return "web/shop/check_out";
    }

    @GetMapping("/shopping-cart")
    String shoppingCart() {
        return "web/shop/shopping_cart";
    }

    @GetMapping("/reviews")
    String reviews() { return "web/shop/reviews"; }

    @GetMapping("/profile-settings")
    String profileSettings() { return "web/shop/profile_settings"; }
}
