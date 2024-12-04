package com.fashionweb.service;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import com.fashionweb.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ICartItemService {

    List<CartItem> getAll();
    List<CartItem> getAllByAccId(Long accId);
    Optional<CartItem> getById(CartItemsId id);
    void addCartItem(CartItem cartDetail);
    void updateCartItem(CartItem cartDetail);
    void deleteCartItem(CartItemsId id);
    void deleteCart(Long accId);
    List<Product> getProductsInCartByAccId(Long accId);

}
