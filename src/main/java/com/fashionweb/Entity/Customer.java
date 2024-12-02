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

    // Thêm cartId vào Customer để lưu thông tin về giỏ hàng
    private Long cartId;  // Trường này lưu cartId của giỏ hàng (không cần bảng Cart nữa)

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accountId")
    private Account account;

    @PrePersist
    public void setDefaultRole() {
        if (this.account != null && this.account.getRole() == null) {
            this.account.setRole("user");
        }
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdReview> reviews;
}
