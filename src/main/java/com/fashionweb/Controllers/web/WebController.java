package com.fashionweb.Controllers.web;

import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class WebController {
    @Autowired
    AccountService accountService;
    @Autowired
    AddressService addressService;

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
    String managerAddress(Model model){
//        var context = SecurityContextHolder.getContext();
//        String email = context.getAuthentication().getName();
//
//        Account account = accountService.getAccounts(email).orElseThrow(
//                () -> new RuntimeException("Không tìm thấy người dùng"));
//        List<Address> addressList = addressService.findAddressByAccount(account);
//        model.addAttribute("addresses", addressList);
        return "web/manager_address";
    }

    @PostMapping("/deladdr/{id}")
    String deleteAccount(@PathVariable long id, Model model){
        if (addressService.deleteAddress(id)) {
            model.addAttribute("errorMessage", "Xóa thành công");
        }
        return "web/manager_address";
    }
}
