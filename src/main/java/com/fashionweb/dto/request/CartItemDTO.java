package com.fashionweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Integer quantity;
    private Double price;
    private LocalDate createDate;
    private String sizeName;
    private Long prodId;
}
