package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.request.VerifyAccountDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import com.fashionweb.service.AuthenticationService;
import com.fashionweb.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class Password {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("forgotpassword/sendcode")
    public String sendcodeforgotpassword(@RequestParam String email, Model model) {
        try {

            // Giả sử 'time' là đối tượng LocalDateTime
            Account account = authenticationService.sendcodeforgotpassword(email);

            // Thêm thuộc tính time vào Model dưới dạng chuỗi ISO-8601
            model.addAttribute("email", account.getEmail());

            return "web/forgot-password-verify-code";
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "web/forgot-password";
        }
    }

    @PostMapping("forgotpassword/sendcode/verifycode")
    public String verifycode(@RequestBody VerifyAccountDTO verifyAccountDTO, Model model) {
        Account account = authenticationService.verifyUser(verifyAccountDTO);

        // Thêm thuộc tính
        model.addAttribute("email", account.getEmail());
        return "web/reset-password";
    }

    @PostMapping("forgotpassword/sendcode/verifycode/resetpass")
    @ResponseBody
    public void resetPassword(@RequestBody RegisterAccountDTO registerAccountDTO, Model model) {
        Account account = authenticationService.resetPassword(registerAccountDTO);
    }

    @GetMapping("account/changePassword")
    String changePassword() {
        return "web/change_password";
    }

}
