package com.fashionweb.service.Impl;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
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
    public List<CartItem> getAll() {
        return cartDetailRepos.findAll();
    }

    @Override
    public Optional<CartItem> getById(Long id) {
        return cartDetailRepos.findById(id);        //! Long
    }

    @Override
    public Optional<CartItem> getById(CartItemsId id) {
        return cartDetailRepos.findById(id);        //! CartDetailsId
    }

    @Override
    public void addCartDetail(CartItem cartDetail) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<CartItem> existingCartItem = cartDetailRepos.findById(cartDetail.getId());

        if (existingCartItem.isPresent()) {
            // Nếu đã tồn tại, tăng số lượng lên 1
            CartItem existingItem = existingCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartDetailRepos.save(existingItem);
        } else {
            // Nếu chưa tồn tại, thêm mới vào giỏ hàng
            cartDetailRepos.save(cartDetail);
        }
    }

    @Override
    public void updateCartDetail(CartItem cartDetail) {
//        if (cartDetailRepos.existsById(cartDetail.getId())) {   //! CartDetailsId
//            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
//                                                                        + cartDetail.getId().getProdId() + ", "
//                                                                        + cartDetail.getId().getSize()
//                                                                        + ")");
//        }

        cartDetailRepos.save(cartDetail);
    }

    @Override
    public void deleteCartDetail(CartItem cartDetail) {
//        if (cartDetailRepos.existsById(cartDetail.getId())) {   //! CartDetailsId
//            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartDetail.getId().getCartId() + ", "
//                                                                        + cartDetail.getId().getProdId() + ", "
//                                                                        + cartDetail.getId().getSize()
//                                                                        + ")");
//        }

        cartDetailRepos.delete(cartDetail);
    }

    @Override
    public void deleteCart(Long id) {
        if (cartDetailRepos.existsById(id)) {            //! Long
            throw new RuntimeException("Không tìm thấy 'Cart' với cartId(" + id + ")");
        }

        cartDetailRepos.deleteById(id);
    }

}
