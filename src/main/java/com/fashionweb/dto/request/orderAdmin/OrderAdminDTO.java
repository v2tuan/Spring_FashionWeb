package com.fashionweb.dto.request.orderAdmin;

import com.fashionweb.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdminDTO {
    private Long orderId;
    private LocalDate createDate;
    private Double total;
    private OrderStatus status;

    private Long accountId;
    private Long discountId;
    private Long addressId;
}
