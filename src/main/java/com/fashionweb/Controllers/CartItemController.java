package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import com.fashionweb.Entity.ProdImage;
import com.fashionweb.Model.Response;
import com.fashionweb.dto.request.CartItemDTO;
import com.fashionweb.dto.request.discount.DiscountDTO;
import com.fashionweb.mapper.ICartItemMapper;
import com.fashionweb.service.ICartItemService;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.ProdImageService;
import com.fashionweb.service.Impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account/cart-items")
public class CartItemController {
    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartItemMapper cartItemMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProdImageService prodImageService;

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
                            itemDetails.put("productImage", prodImageService.findImageNamesByProdId(item.getId().getProdId()).getFirst());
                        });

                        return itemDetails;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(simplifiedCartItems);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }
    @PutMapping("/update/{prodId}/{sizeName}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long prodId, @PathVariable String sizeName, @RequestBody @Valid CartItemDTO cartItemDTO) {
        // Lấy thông tin người dùng hiện tại
        var context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
        }

        String email = context.getAuthentication().getName();
        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));

        // Tạo CartItemsId từ thông tin người dùng và sản phẩm
        CartItemsId cartItemsId = new CartItemsId(account.getAccId(), sizeName, prodId);

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        var existingCartItem = cartItemService.getCartItemById(cartItemsId);
        if (existingCartItem.isEmpty()) {
            return ResponseEntity.status(404).body("Sản phẩm không có trong giỏ hàng.");
        }

        // Lấy đối tượng CartItem đã tồn tại và cập nhật lại số lượng và thông tin mới
        CartItem existingItem = existingCartItem.get();
        existingItem.setQuantity(cartItemDTO.getQuantity());
        existingItem.setPrice(cartItemDTO.getPrice()); // Nếu có thay đổi giá cả

        // Cập nhật sản phẩm trong giỏ hàng
        cartItemService.updateCartItem(existingItem);

        return new ResponseEntity<Response>(new Response(true, "Thành công", "Update thanh cong"), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{prodId}/{sizeName}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long prodId, @PathVariable String sizeName) {
        // Lấy thông tin người dùng hiện tại
        var context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
        }

        String email = context.getAuthentication().getName();
        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));

        // Tạo CartItemsId từ thông tin người dùng và sản phẩm
        CartItemsId cartItemsId = new CartItemsId(account.getAccId(), sizeName, prodId);

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        var existingCartItem = cartItemService.getCartItemById(cartItemsId);
        if (existingCartItem.isEmpty()) {
            return ResponseEntity.status(404).body("Sản phẩm không có trong giỏ hàng.");
        }

        // Xóa sản phẩm khỏi giỏ hàng
        cartItemService.removeCartItem(cartItemsId);

        return new ResponseEntity<Response>(new Response(true, "Thành công", "Xóa sản phẩm thành công"), HttpStatus.OK);
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<?> removeAllCartItems() {
        // Lấy thông tin người dùng hiện tại từ SecurityContext
        var context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(401).body("Người dùng chưa đăng nhập.");
        }

        // Lấy email của người dùng đã đăng nhập
        String email = context.getAuthentication().getName();
        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng với email: " + email));

        // Lấy tất cả sản phẩm trong giỏ hàng của người dùng
        List<CartItem> cartItems = cartItemService.getAllByAccId(account.getAccId());

        // Kiểm tra xem giỏ hàng có sản phẩm hay không
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(404).body("Giỏ hàng trống.");
        }

        // Xóa tất cả sản phẩm khỏi giỏ hàng
        cartItemService.deleteCart(account.getAccId());

        // Trả về phản hồi thành công
        return new ResponseEntity<>(new Response(true, "Thành công", "Đã xóa tất cả sản phẩm trong giỏ hàng."), HttpStatus.OK);
    }

    @PostMapping("/apply-coupon")
    public ResponseEntity<?> applyCoupon(@RequestBody Map<String, String> body) {
        try {

            String couponCode = body.get("couponCode");  // Lấy mã coupon từ JSON
            DiscountDTO discountDetails = cartItemService.getDiscountDetail(couponCode);  // Lấy tỷ lệ giảm giá
            return ResponseEntity.ok(discountDetails);  // Trả về tỷ lệ giảm giá
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid coupon code");
        }
    }
}
