package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String email;
    private String password;
    private String profilePic;
    private String role;
    private String status;

    @OneToOne(mappedBy = "account")
    private Customer customer;
}
