package com.fashionweb.repository;

import com.fashionweb.Entity.CartDetail;
import com.fashionweb.Entity.CartDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {

    boolean existsById(CartDetailsId id);

    Optional<CartDetail> findById(CartDetailsId id);
}
