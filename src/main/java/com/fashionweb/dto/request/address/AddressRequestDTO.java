package com.fashionweb.dto.request.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {
    private String fullName;
    private String addr;
    private String phone;
}