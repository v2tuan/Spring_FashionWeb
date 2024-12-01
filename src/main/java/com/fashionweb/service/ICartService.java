package com.fashionweb.service;

import com.fashionweb.Entity.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartService {

    List<Cart> getAll();
    Optional<Cart> getById(Long id);
    void createCart(Cart cart);
    void updateCart(Cart cart);
    void deleteCart(Long id);
}
