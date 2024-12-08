    package com.fashionweb.dto.request.brand;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BrandDTO2 {
        private Long brandId;
        private String brandName;
        private String images;
        private Long prodCount;
    }
