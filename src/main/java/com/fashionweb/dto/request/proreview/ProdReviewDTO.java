package com.fashionweb.dto.request.proreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdReviewDTO {
    private Long prodId;
    private Long accId;
    private String comment;
    private String images;
    private Integer rating;

}
