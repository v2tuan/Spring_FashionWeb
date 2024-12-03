package com.fashionweb.service;

import com.fashionweb.Entity.Order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    <S extends Order> S save(S order);
    List<Order> findAll();
    Optional<Order> findById(Long orderId);
    void deleteById(Long orderId);
    Optional<Order> findByCreateDate(Date createDate);
    Optional<Order> findByStatus(String status);
}
