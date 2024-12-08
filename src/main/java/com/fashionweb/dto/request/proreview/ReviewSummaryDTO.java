package com.fashionweb.dto.request.proreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSummaryDTO {
    private Long accId;
    private String fullname;
    private String comment;
    private String images;
    private Integer rating;

}
