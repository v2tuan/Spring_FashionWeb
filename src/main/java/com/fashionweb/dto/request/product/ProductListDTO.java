package com.fashionweb.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private Long prodId;
    private String prodName;
    private String description;
    private Double regular;
    private Double promo;
    private Boolean status;
    private Date createDate;
    private Long brandId;
    private Long subCateId;
    private String imgURL;
}
