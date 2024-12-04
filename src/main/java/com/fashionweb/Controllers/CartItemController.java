package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.dto.request.CartItemDTO;
import com.fashionweb.mapper.ICartItemMapper;
import com.fashionweb.service.ICartItemService;
import com.fashionweb.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartItemMapper cartItemMapper;

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            // Chuyển đổi DTO sang Entity
            CartItem cartItem = cartItemMapper.toCartItem(cartItemDTO);

            var context = SecurityContextHolder.getContext();
            if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
            }
            String email = context.getAuthentication().getName();

            Account account = accountService.getAccounts(email).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng"));
            cartItem.setAccount(account);

            // Gọi service để thêm sản phẩm vào giỏ hàng
            cartItemService.addCartItem(cartItem);


            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
}
