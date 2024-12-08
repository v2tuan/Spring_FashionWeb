package com.fashionweb.dto.request.orderAdmin;

import com.fashionweb.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailAdminDTO {
    private Long accId;
    private Long orderId;
    private LocalDate createDate;

    private String fullname;
    private String email;
    private String phone;

    private String address;
    private String adrFullName;
    private String adrPhone;


    private Double subTotal;
    private OrderStatus status;
}
