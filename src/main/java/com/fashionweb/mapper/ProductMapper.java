package com.fashionweb.mapper;

import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Ánh xạ name và price, nhưng loại trừ description
    @Mapping(source = "images", target = "images", ignore = true)
    Product toProduct(ProductDTO productDTO);
    @Mapping(source = "images", target = "images", ignore = true)

    void updateProduct(@MappingTarget Product product, ProductDTO productDTO);
}
