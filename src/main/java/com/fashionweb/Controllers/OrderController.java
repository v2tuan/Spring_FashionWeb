package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
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
}
