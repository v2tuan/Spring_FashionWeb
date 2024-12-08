package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Order;
import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.dto.request.orderAdmin.OrderAdminDTO;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/account/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            var context = SecurityContextHolder.getContext();
            if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
            }

            String email = context.getAuthentication().getName();
            Account account = accountService.getAccounts(email).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng"));

            orderDTO.setAccountId(account.getAccId());  // Giả sử OrderDTO có thuộc tính accountId

            // Tiến hành tạo đơn hàng
            orderService.createOrder(orderDTO);

            return ResponseEntity.ok("Đơn hàng đã được tạo thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @GetMapping
    public String showOrderList(
            @RequestParam(value = "orderDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDate,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        var context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            return "redirect:/login";
        }

        String email = context.getAuthentication().getName();
        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));

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

        // Cấu hình pagination
        Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách đơn hàng đã lọc
        Page<OrderAdminDTO> orders = orderService.findAllOrderListPageableById(account.getAccId(), orderDate, status, pageable);

        // Gửi dữ liệu sang view
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());


        return "web/shop/order_list";
    }
}
