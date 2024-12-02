package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String voucher;
    private String description;
    private Double discountPercentage;
    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "discount", orphanRemoval = false)  // Không xóa Order khi xóa Discount
    private List<Order> orders;
}
