package com.fashionweb.Controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthenticationController {
    @GetMapping("/login")
    String login() {
        return "web/user-login";
    }

    @GetMapping("/register")
    String register() {
        return "web/user-register";
    }

    @GetMapping("/login/forgotpassword")
    String forgotPassword(){
        return "web/forgot-password";
    }

    @GetMapping("/login/forgotpassword/verifycode")
    String forgotPasswordVerifyCode(){
        return "web/forgot-password-verify-code";
    }

    @GetMapping("/login/forgotpassword/verifycode/resetpassword")
    String resetPassword(){
        return "web/reset-password";
    }
}
