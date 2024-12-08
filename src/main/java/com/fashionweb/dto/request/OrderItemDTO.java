package com.fashionweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private String sizeName; // Kích thước sản phẩm
    private Long prodId; // ID sản phẩm
    private Integer quantity; // Số lượng
    private Double price; // Giá từng món
}
