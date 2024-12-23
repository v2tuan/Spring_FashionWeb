package com.fashionweb.service;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.discount.DiscountDTO;

import java.util.List;
import java.util.Optional;

public interface ICartItemService {

    List<CartItem> getAll();
    List<CartItem> getAllByAccId(Long accId);
    Optional<CartItem> getById(CartItemsId id);
    void addCartItem(CartItem cartDetail);
    Optional<CartItem> getCartItemById(CartItemsId cartItemsId);
    void updateCartItem(CartItem cartDetail);
    void deleteCart(Long accId);
    void removeCartItem(CartItemsId cartItemsId);
    List<Product> getProductsInCartByAccId(Long accId);
    DiscountDTO getDiscountDetail(String couponCode);
}
