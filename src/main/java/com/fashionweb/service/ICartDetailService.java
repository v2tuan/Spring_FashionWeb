package com.fashionweb.service;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService {

    List<CartItem> getAll();
    Optional<CartItem> getById(Long id);
    Optional<CartItem> getById(CartItemsId id);
    void addCartDetail(CartItem cartDetail);
    void updateCartDetail(CartItem cartDetail);
    void deleteCartDetail(CartItem cartDetail);
    void deleteCart(Long id);

}
