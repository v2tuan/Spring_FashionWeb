package com.fashionweb.Controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @GetMapping("/addproduct")
    String addproduct(){
        return "admin/addOrEditProduct";
//       return "admin/test";
    }

    @GetMapping("/productlist")
    String product_list(){
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
