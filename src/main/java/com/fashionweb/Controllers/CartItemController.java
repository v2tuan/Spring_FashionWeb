package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import com.fashionweb.dto.request.CartItemDTO;
import com.fashionweb.mapper.ICartItemMapper;
import com.fashionweb.service.ICartItemService;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartItemMapper cartItemMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
        try {
            // Lấy thông tin người dùng hiện tại
            var context = SecurityContextHolder.getContext();
            if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
            }

            String email = context.getAuthentication().getName();
            Account account = accountService.getAccounts(email).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng"));

            CartItem cartItem = cartItemMapper.toCartItem(cartItemDTO);

            // Tạo và gán CartItemsId
            CartItemsId cartItemsId = new CartItemsId(account.getAccId(), cartItemDTO.getSizeName(), cartItemDTO.getProdId());
            cartItem.setId(cartItemsId);
            cartItem.setAccount(account);

            var existingCartItem = cartItemService.getCartItemById(cartItemsId);
            if (existingCartItem.isPresent()) {
                // Nếu đã tồn tại, cập nhật số lượng
                CartItem existingItem = existingCartItem.get();
                existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
                cartItemService.updateCartItem(existingItem);
            } else {
                // Nếu chưa tồn tại, thêm mới
                cartItemService.addCartItem(cartItem);
            }

            return ResponseEntity.ok("Sản phẩm đã được thêm/cập nhật trong giỏ hàng.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCartItems() {
        try {
            // Lấy thông tin người dùng hiện tại
            var context = SecurityContextHolder.getContext();
            if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
            }

            String email = context.getAuthentication().getName();
            Account account = accountService.getAccounts(email).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng"));

            // Tìm tất cả các sản phẩm trong giỏ hàng của tài khoản
            var cartItems = cartItemService.getAllByAccId(account.getAccId());

            List<Map<String, Object>> simplifiedCartItems = cartItems.stream()
                    .map(item -> {
                        Map<String, Object> itemDetails = new HashMap<>();
                        itemDetails.put("id", item.getId());
                        itemDetails.put("quantity", item.getQuantity());
                        itemDetails.put("price", item.getPrice());
                        itemDetails.put("createDate", item.getCreateDate());

                        var product = productService.getProduct(item.getId().getProdId());
                        product.ifPresent(p -> {
                            itemDetails.put("productName", p.getProdName());
                            itemDetails.put("productImage", p.getImages());
                        });

                        return itemDetails;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(simplifiedCartItems);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }
}
