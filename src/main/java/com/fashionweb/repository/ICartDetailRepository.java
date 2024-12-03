package com.fashionweb.repository;

import com.fashionweb.Entity.CartItem;
import com.fashionweb.Entity.Embeddable.CartItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartItem, Long> {

    boolean existsById(CartItemsId id);
    Optional<CartItem> findById(CartItemsId id);
    List<CartItem> findAllByAccountAccId(Long accountId);

}
