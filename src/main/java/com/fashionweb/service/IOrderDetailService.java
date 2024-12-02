package com.fashionweb.service;

import com.fashionweb.Entity.OrderDetail;
import com.fashionweb.Entity.OrderDetailsId;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    <S extends OrderDetail> S save(S orderDetail);
    List<OrderDetail> findAll();
    Optional<OrderDetail> findById(OrderDetailsId orderDetailId);
    void deleteById(OrderDetailsId orderDetailId);
}
