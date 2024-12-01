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
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;

    private String custName;
    private String custPhone;
    private String address;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @PrePersist
    public void setDefaultRole() {
        if (this.account != null && this.account.getRole() == null) {
            this.account.setRole("user");
        }
    }

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private List<ProdReview> reviews;
}
