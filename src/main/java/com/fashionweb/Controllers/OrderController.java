package com.fashionweb.Controllers;

import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.service.Impl.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody @Valid OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return ResponseEntity.ok("Đơn hàng đã được tạo thành công!");
    }
}
