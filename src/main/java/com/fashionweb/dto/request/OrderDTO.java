package com.fashionweb.dto.request;

import com.fashionweb.Entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long accountId; // ID của tài khoản đặt hàng
    private Long discountId; // (Tùy chọn) ID của mã giảm giá
    private Long addressId; // ID của địa chỉ
    private List<OrderItemDTO> items; // Danh sách sản phẩm
}