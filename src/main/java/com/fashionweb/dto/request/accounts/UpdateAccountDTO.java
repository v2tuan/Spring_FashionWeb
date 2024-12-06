package com.fashionweb.dto.request.accounts;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountDTO {
    @Email
    private String email;
    private String avatar;
    private String fullname;
    private String address;
    private String phone;
    private String gender;
    private String enabled;
    private MultipartFile file;
}
