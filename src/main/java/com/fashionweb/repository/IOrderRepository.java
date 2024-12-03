package com.fashionweb.repository;

import com.fashionweb.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCreateDate(Date createDate);
    Optional<Order> findByStatus(String status);
}
