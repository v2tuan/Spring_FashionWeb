package com.fashionweb.dto.request.accounts;

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
    private String avatar;
    private String fullname;
    private String address;
    private String phone;
    private String gender;
    private String enabled;
}
