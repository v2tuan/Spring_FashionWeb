package com.fashionweb.service;

import com.fashionweb.Entity.CartDetail;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService {

    List<CartDetail> getAll();
    Optional<CartDetail> getById(Long id);
    void addCartDetail(CartDetail cartDetail);
    void updateCartDetail(CartDetail cartDetail);
    void deleteCartDetail(CartDetail cartDetail);
    void deleteCart(Long id);

}
