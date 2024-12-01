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
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Date orderDate;
    private Double totalAmount;
    private String status;
    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "custId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "discountId", nullable = true)
    private Discount discount;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
