package com.fashionweb.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product2DTO {
    private Long prodId;
    private String prodName;
    private String description;
    private Double regular;
    private Double promo;
    private Boolean status;

    private Long brandId;
    private Long subCateId;
    private List<String> imgURLs;
    private List<SizeDTO> sizeDTOs;
}
