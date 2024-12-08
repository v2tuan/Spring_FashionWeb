package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Account;
import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.orderAdmin.OrderAdminDTO;
import com.fashionweb.dto.response.AccountResponse;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class DashboardController {

    @Autowired
    AccountService accountService;
    @Autowired
    IAccountMapper accountMapper;
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/dashboard")
    public String showOrderList(
            @RequestParam(value = "orderDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDate,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        // Chuyển giá trị orderStatus thành enum OrderStatus nếu có
        OrderStatus status = null;
        if (orderStatus != null && !orderStatus.isEmpty()) {
            try {
                status = OrderStatus.valueOf(orderStatus); // Chuyển chuỗi thành enum
            } catch (IllegalArgumentException e) {
                // Nếu giá trị không hợp lệ, không làm gì
                status = null;
            }
        }

        // Lấy các thông tin tổng quan
        Double totalRevenue = orderService.getTotalRevenue();
        long totalOrders = orderService.countTotalOrders();
        Long totalProductsSold = orderService.getTotalProductsSold();

        // Cấu hình pagination
        Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách đơn hàng đã lọc
        Page<OrderAdminDTO> orders = orderService.findAllOrderListPageable(orderDate, status, pageable);

        // Gửi dữ liệu sang view
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalProductsSold", totalProductsSold);

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
