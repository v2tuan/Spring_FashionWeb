    package com.fashionweb.dto.request.brand;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.web.multipart.MultipartFile;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BrandDTO2 {
        private Long brandId;
        private String brandName;
        private String images;
        private Long prodCount;
    }
