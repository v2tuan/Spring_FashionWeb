package com.fashionweb.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryGridDTO {
    private Long cateId;
    private String cateName;
    private Map<Long, String> subcates;
}
