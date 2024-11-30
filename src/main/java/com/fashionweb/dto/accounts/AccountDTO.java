package com.fashionweb.dto.accounts;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String profilePic;
    private String role;
    private String status;
}
