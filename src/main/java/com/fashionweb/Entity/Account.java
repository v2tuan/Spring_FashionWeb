package com.fashionweb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private String profilePic;
    private String role;
    private Boolean status;

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

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

}
