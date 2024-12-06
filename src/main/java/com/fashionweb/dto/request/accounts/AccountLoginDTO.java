package com.fashionweb.dto.request.accounts;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountLoginDTO {

    @NotBlank(message = "Username/Phone/Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
