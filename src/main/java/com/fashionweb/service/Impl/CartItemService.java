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
    public void addCartItem(CartItem cartItem) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<CartItem> existingCartItem = cartItemRepos.findById(cartItem.getId());

        if (existingCartItem.isPresent()) {
            // Nếu đã tồn tại, tăng số lượng lên 1
            CartItem existingItem = existingCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepos.save(existingItem);
        } else {
            // Nếu chưa tồn tại, thêm mới vào giỏ hàng
            cartItemRepos.save(cartItem);
        }
    }

    public Optional<CartItem> getCartItemById(CartItemsId cartItemsId) {
        return cartItemRepos.findById(cartItemsId);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        // Check if the CartItem exists in the repository
        Optional<CartItem> existingCartItem = cartItemRepos.findById(cartItem.getId());

        if (existingCartItem.isEmpty()) {
            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + cartItem.getId().getAccId() + ", "
                    + cartItem.getId().getProdId() + ", "
                    + cartItem.getId().getSizeName() + ")");
        }

        // If found, save the updated CartItem
        cartItemRepos.save(cartItem);
    }


    public void deleteCartItem(CartItemsId id) {

    }

    @Override
    public void removeCartItem(CartItemsId cartItemsId) {
        Optional<CartItem> cartItem = cartItemRepos.findById(cartItemsId);
        if (cartItem.isPresent()) {
            cartItemRepos.delete(cartItem.get());
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
    }

    @Override
    public void deleteCart(Long accId) {
        List<CartItem> cartItems = cartItemRepos.findAllByAccountAccId(accId);
        for (CartItem cartItem : cartItems) {
            cartItemRepos.delete(cartItem);
        }
    }

    @Override
    public List<Product> getProductsInCartByAccId(Long accId) {
        // #

        return null;
    }

}
