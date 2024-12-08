package com.fashionweb.dto.request.proreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdReviewDTO {
    private Long accId;
    private Long prodId;
    private String comment;
    private LocalDate reviewDate;
    private String image;
    private Integer rating;

    private MultipartFile file;
}
