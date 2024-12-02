package com.fashionweb.service;

import com.fashionweb.Entity.CartDetail;
import com.fashionweb.Entity.CartDetailsId;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService {

    List<CartDetail> getAll();
    Optional<CartDetail> getById(Long id);
    Optional<CartDetail> getById(CartDetailsId id);
    void addCartDetail(CartDetail cartDetail);
    void updateCartDetail(CartDetail cartDetail);
    void deleteCartDetail(CartDetail cartDetail);
    void deleteCart(Long id);

}
