package com.fashionweb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accId;

    @Email
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String avatar;
    private String fullname;
    private String address;
    private String phone;

    @Column(nullable = false, unique = true)
    private Long cartId;

    private String role;

    @Column(nullable = false)
    private Boolean status = false;

    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;
    private boolean enabled;

    @PrePersist
    public void setDefaultValues() {
        // Đảm bảo role mặc định là "user" nếu không có giá trị
        if (this.role == null || this.role.isEmpty()) {
            this.role = "user"; // Gán giá trị mặc định là "user"
        }
        // Mã hóa mật khẩu
        if (this.password != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(this.password);
        }
    }

    // Kiểm tra mật khẩu khi người dùng đăng nhập
    public boolean checkPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, this.password); // So sánh mật khẩu nhập vào với mật khẩu đã mã hóa
    }

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdReview> reviews;
}
