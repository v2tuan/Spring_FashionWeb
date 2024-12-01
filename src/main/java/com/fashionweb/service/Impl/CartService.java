package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Cart;
import com.fashionweb.repository.ICartRepository;
import com.fashionweb.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    ICartRepository cartRepos;

    @Override
    public List<Cart> getAll() {
        return cartRepos.findAll();
    }

    @Override
    public Optional<Cart> getById(Long id) {
        return cartRepos.findById(id);
    }

    @Override
    public void createCart(Cart cart) {
        cartRepos.save(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepos.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        if (!cartRepos.existsById(id)) {
            throw new RuntimeException("Không tìm thấy 'Cart' với id(" + id + ")");
        }

        cartRepos.deleteById(id);
    }
}
