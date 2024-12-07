package com.fashionweb.dto.request.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private Long discountId;

    private String voucher;
    private String description;
    private Double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createDate;

    private List<Long> orderIds;
}
