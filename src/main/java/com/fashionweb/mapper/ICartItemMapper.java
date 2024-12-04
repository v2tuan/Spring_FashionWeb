package com.fashionweb.mapper;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.dto.request.CartItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartItemMapper {
    CartItem toCartItem(CartItemDTO cartItemDTO);
}
