package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Account;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.response.AccountResponse;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {
    @Autowired
    AccountService accountService;
    @Autowired
    IAccountMapper accountMapper;

    @GetMapping("/admin/dashboard")
    String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/userlist")
    String UserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String role,
            Model model) {

        Role enumRole = null;

        if (role != null && !role.isEmpty()) {
            try {
                enumRole = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value: " + role);
            }
        }
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable với trang và kích thước
        Page<AccountResponse> accountList = accountService.findAllAccounts(fullname, enabled, enumRole, pageable);

        model.addAttribute("account", accountList.getContent()); // Truyền danh sách sản phẩm
        model.addAttribute("currentPage", accountList.getNumber()); // Trang hiện tại
        model.addAttribute("totalPages", accountList.getTotalPages()); // Tổng số trang
        return "admin/user_list";
    }

    @GetMapping("/admin/userlist_api")
    @ResponseBody
    Page<AccountResponse> UserList_api(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String role,
            Model model) {
        Role enumRole = null;

        if (role != null && !role.isEmpty()) {
            try {
                enumRole = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value: " + role);
            }
        }
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable với trang và kích thước
        Page<AccountResponse> accountList = accountService.findAllAccounts(fullname, enabled, enumRole, pageable);

        return accountList;
    }

    @PostMapping("/admin/addmanager")
    String addManager(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Account account = accountService.getAccounts(id).orElseThrow(() ->new RuntimeException("Người dùng không tồn tại"));
        account.setRole(Role.MANAGER);
        AccountDTO accountDTO = accountMapper.toAccountDTO(account);
        accountService.updateAccount(account.getAccId(), accountDTO);
        // Sử dụng RedirectAttributes để thêm thông báo
        redirectAttributes.addFlashAttribute("alert", "Đã chuyển thành Role Manager");
        // Chuyển hướng tới trang danh sách người dùng sau khi thêm quản lý
        return "redirect:/admin/userlist";
    }

    @PostMapping("/admin/removemanager")
    String RemoveManager(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Account account = accountService.getAccounts(id).orElseThrow(() ->new RuntimeException("Người dùng không tồn tại"));
        account.setRole(Role.USER);
        AccountDTO accountDTO = accountMapper.toAccountDTO(account);
        accountService.updateAccount(account.getAccId(), accountDTO);
        // Sử dụng RedirectAttributes để thêm thông báo
        redirectAttributes.addFlashAttribute("alert", "Đã chuyển về Role User");
        // Chuyển hướng tới trang danh sách người dùng sau khi thêm quản lý
        return "redirect:/admin/userlist";
    }
}
