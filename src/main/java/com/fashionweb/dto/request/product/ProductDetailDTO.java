package com.fashionweb.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Long prodId;
    private String prodName;
    private Double regular;
    private Double promo;
    private Date createDate;
    private Boolean isSale;
    private Integer percent;
    private String brandName;
    private String subCateName;
    private Double rating;
    private Long reviewCount;
    private List<String> imgURL;
    private List<SizeDTO> sizeDTOs;
}
