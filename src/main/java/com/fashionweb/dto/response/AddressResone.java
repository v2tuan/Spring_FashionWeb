package com.fashionweb.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResone {
    private Long id;

    private String fullName;
    private String addr;
    private String phone;
}
