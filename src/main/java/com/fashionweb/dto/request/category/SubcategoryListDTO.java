package com.fashionweb.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryListDTO {
    private Long subCateId;
    private String subCateName;
}
