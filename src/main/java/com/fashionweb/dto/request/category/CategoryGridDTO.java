package com.fashionweb.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryGridDTO {
    public Long cateId;
    public String cateName;
    public List<SubcategoryListDTO> subcates;
}
