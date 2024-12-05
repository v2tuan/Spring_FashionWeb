package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String addr;
    private String phone;

    @OneToMany
    @JoinColumn(name = "orderId")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "accId") // Cột accId trong bảng Address
    private Account account;
}
