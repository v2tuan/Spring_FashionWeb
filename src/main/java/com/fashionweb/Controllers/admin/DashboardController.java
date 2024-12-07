package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.response.AccountResponse;
import com.fashionweb.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {
    @Autowired
    AccountService accountService;

    @GetMapping("/admin/dashboard")
    String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/userlist")
    String UserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable với trang và kích thước
        Page<AccountResponse> accountList = accountService.findAllAccounts(pageable);

        model.addAttribute("account", accountList.getContent()); // Truyền danh sách sản phẩm
        model.addAttribute("currentPage", accountList.getNumber()); // Trang hiện tại
        model.addAttribute("totalPages", accountList.getTotalPages()); // Tổng số trang
        return "admin/user_list";
    }
}
