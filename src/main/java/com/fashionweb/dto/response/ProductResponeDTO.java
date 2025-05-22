package com.fashionweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponeDTO {
    Long prodId;
    String prodName;
    Double regular;
    Double promo;
    String imgURL;
}
