package com.fashionweb.dto.request.product;

import com.fashionweb.Entity.Brand;
import com.fashionweb.Entity.ProdImage;
import com.fashionweb.Entity.Size;
import com.fashionweb.Entity.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDTO {
    private Long prodId;
    private String prodName;
    private String description;
    private Double regular;
    private Double promo;
    private Boolean status;
    private Integer totalQuantity;
    private Date createDate;
    private Long brandId;
    private String brandName; // Nếu cần tên thương hiệu
    private Long subCatId;
    private String subCatName; // Nếu cần tên subcategory
    private List<String> imageUrls; // Đường dẫn ảnh
    private List<String> sizeNames; // Tên các kích cỡ

}
