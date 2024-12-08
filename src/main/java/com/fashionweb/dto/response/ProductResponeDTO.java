package com.fashionweb.dto.response;

import lombok.Data;

@Data
public class ProductResponeDTO {
    String prodName;
    Double regular;
    Double promo;
    String imgURL;

    // Constructor
    public ProductResponeDTO(String prodName, Double regular, Double promo, String imgURL) {
        this.prodName = prodName;
        this.regular = regular;
        this.promo = promo;
        this.imgURL = imgURL;
    }
}
