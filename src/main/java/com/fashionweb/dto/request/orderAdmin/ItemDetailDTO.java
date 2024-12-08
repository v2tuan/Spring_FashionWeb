package com.fashionweb.dto.request.orderAdmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailDTO {
    private Long productId;
    private String imgURL;
    private String prodName;
    private String sizeName;
    private Double unitPrice;
    private Integer quantity;
}
