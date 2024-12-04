package com.fashionweb.service.Impl;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import com.fashionweb.Entity.Product;
import com.fashionweb.repository.ICartItemRepository;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService implements ICartItemService {

    @Autowired
    ICartItemRepository cartItemRepos;
    @Autowired
    IProductRepository productRepos;

    @Override
    public List<CartItem> getAll() {
        return cartItemRepos.findAll();
    }

    @Override
    public List<CartItem> getAllByAccId(Long accId) {
        return cartItemRepos.findAllByAccountAccId(accId);
    }

    @Override
    public Optional<CartItem> getById(CartItemsId id) {
        return cartItemRepos.findById(id);
    }

    @Override
    public void addCartItem(CartItem cartDetail) {
//        if (cartDetailRepos.existsById(cartDetail.getId())) {
//            throw new RuntimeException("'Cart' với id(" + cartDetail.getId().getCartId() + ", "
//                                                        + cartDetail.getId().getProdId() + ", "
//                                                        + cartDetail.getId().getSize()
//                                                        + ") đã tồn tại");
//        }

        cartItemRepos.save(cartDetail);
    }

    @Override
    public void updateCartItem(CartItem cartDetail) {
//        if (cartDetailRepos.existsById(cartDetail.getId())) {
//            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
//                                                                        + cartDetail.getId().getProdId() + ", "
//                                                                        + cartDetail.getId().getSize()
//                                                                        + ")");
//        }

        cartItemRepos.save(cartDetail);
    }

    @Override
    public void deleteCartItem(CartItemsId id) {
//        if (cartDetailRepos.existsById(cartDetail.getId())) {
//            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
//                                                                        + cartDetail.getId().getProdId() + ", "
//                                                                        + cartDetail.getId().getSize()
//                                                                        + ")");
//        }

        cartItemRepos.deleteCartItemById(id);
    }

    @Override
    public void deleteCart(Long accId) {
        if (cartItemRepos.existsById(accId)) {            //! Long
            throw new RuntimeException("Không tìm thấy 'Cart' với cartId(" + accId + ")");
        }

        cartItemRepos.deleteById(accId);
    }

    @Override
    public List<Product> getProductsInCartByAccId(Long accId) {
        // #

        return null;
    }

}
