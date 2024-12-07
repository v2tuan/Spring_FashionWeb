package com.fashionweb.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryGridDTO {
    private Long subCateId;
    private String subCateName;
    private List<Long> prodIds;
}
