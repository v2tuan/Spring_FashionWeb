package com.fashionweb.mapper;

import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.product.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
}
