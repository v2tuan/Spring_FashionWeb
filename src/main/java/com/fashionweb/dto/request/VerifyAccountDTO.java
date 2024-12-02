package com.fashionweb.dto.request;

import lombok.Data;

@Data
public class VerifyAccountDTO {
    private String email;
    private String verificationCode;
}
