package com.fashionweb.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String prodName;

    private String description;
    private Double regular;

    private Double promo;

    private Boolean status;

    private Long brandId;
    private Long subCateId;

    private List<String> images;
    private SizeDTO[] sizes;
}
