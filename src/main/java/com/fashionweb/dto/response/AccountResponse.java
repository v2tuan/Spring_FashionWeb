package com.fashionweb.dto.response;

import com.fashionweb.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accId;
    private String email;
    private String avatar;
    private String fullname;
    private boolean enabled;
    private String gender;
    private LocalDate createDate;
    private Role role;
}
