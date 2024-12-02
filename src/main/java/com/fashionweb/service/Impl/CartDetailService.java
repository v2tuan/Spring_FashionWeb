package com.fashionweb.service.Impl;

import com.fashionweb.Entity.CartDetail;
import com.fashionweb.repository.ICartDetailRepository;
import com.fashionweb.service.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailService implements ICartDetailService {

    @Autowired
    ICartDetailRepository cartDetailRepos;

    @Override
    public List<CartDetail> getAll() {
        return cartDetailRepos.findAll();
    }

    @Override
    public Optional<CartDetail> getById(Long id) {
        return cartDetailRepos.findById(id);        //! Long??? CartDetailsId???
    }

    @Override
    public void addCartDetail(CartDetail cartDetail) {
        if (cartDetailRepos.existsById(cartDetail.getId())) {
            throw new RuntimeException("'Cart' với id(" + cartDetail.getId().getCartId() + ", "
                                                        + cartDetail.getId().getProdId() + ", "
                                                        + cartDetail.getId().getSize()
                                                        + ") đã tồn tại");
        }

        cartDetailRepos.save(cartDetail);
    }

    @Override
    public void updateCartDetail(CartDetail cartDetail) {
        if (cartDetailRepos.existsById(cartDetail.getId())) {
            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
                                                                        + cartDetail.getId().getProdId() + ", "
                                                                        + cartDetail.getId().getSize()
                                                                        + ")");
        }

        cartDetailRepos.save(cartDetail);
    }

    @Override
    public void deleteCartDetail(CartDetail cartDetail) {
        if (cartDetailRepos.existsById(cartDetail.getId())) {
            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
                                                                        + cartDetail.getId().getProdId() + ", "
                                                                        + cartDetail.getId().getSize()
                                                                        + ")");
        }

        cartDetailRepos.delete(cartDetail);
    }

    @Override
    public void deleteCart(Long id) {
        if (cartDetailRepos.existsById(id)) {            //! Long??? CartDetailsId???
            throw new RuntimeException("Không tìm thấy 'Cart' với cartId(" + id + ")");
        }

        cartDetailRepos.deleteById(id);
    }
}
