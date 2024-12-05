package com.fashionweb.dto.request.product;

import com.fashionweb.Entity.Brand;
import com.fashionweb.Entity.ProdImage;
import com.fashionweb.Entity.Size;
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
    private String prodName;
    private String description;
    private Double regular;
    private Double promo;

    private String status;
    private Integer totalQuantity;

    private List<ProdImage> prodImages;

    private Date createDate;
    private Brand brand;
    private List<Size> sizes;
}
