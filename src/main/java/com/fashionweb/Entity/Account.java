package com.fashionweb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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

    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String profilePic;
    private String role;
    private String status;

    @OneToOne(mappedBy = "account")
    private Customer customer;
}
