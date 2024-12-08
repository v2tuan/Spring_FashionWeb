    package com.fashionweb.dto.request.product;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGridDTO {
    private Long prodId;
    private String prodName;
    private Double regular;
    private Double promo;
    private Date createDate;
    private Boolean isSale;
    private Integer percent;
    private Boolean isBest;
    private Long brandId;
    private Long subCateId;
    private String imgURL;
    private Double rating;
    private Long reviewCount;
}
