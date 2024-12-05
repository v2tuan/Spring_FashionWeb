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

    @GetMapping("/user-register")
    String userRegister() { return "web/user-register"; }

    @GetMapping("/user-login")
    String userLogin() { return "web/user-login"; }

    @GetMapping("/shop")
    String shop() {
        return "web/shop/shop_content";
    }

    @GetMapping("/product-detail")
    String productDetail() {
        return "web/shop/product_detail";
    }

    @GetMapping("/check-out")
    String checkOut() {
        return "web/shop/check_out";
    }

    @GetMapping("/shopping-cart")
    String shoppingCart() {
        return "web/shop/shopping_cart";
    }

    @GetMapping("/profile")
    String profile(){
        return "web/my_profile";
    }

    @GetMapping("/forgotpassword")
    String forgotPassword(){
        return "web/forgot-password";
    }

    @GetMapping("/forgotpassword/verifycode")
    String forgotPasswordVerifyCode(){
        return "web/forgot-password-verify-code";
    }

    @GetMapping("/forgotpassword/verifycode/resetpassword")
    String resetPassword(){
        return "web/reset-password";
    }

    @GetMapping("/managerAddress")
    String managerAddress(){
        return "web/manager_address";
    }
}
