package com.fashionweb.Entity;

import com.fashionweb.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime createDate;
    private Double total;
    private String status;

    @ManyToOne
    @JoinColumn(name = "discountId", referencedColumnName = "discountId")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "accId", referencedColumnName = "accId")
    private Account account;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}

