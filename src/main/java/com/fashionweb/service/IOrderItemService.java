package com.fashionweb.service;

import com.fashionweb.Entity.OrderItem;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
import com.fashionweb.dto.request.orderAdmin.ItemDetailDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {
    <S extends OrderItem> S save(S orderDetail);
    List<OrderItem> findAll();
    Optional<OrderItem> findById(OrderItemsId orderDetailId);
    void deleteById(OrderItemsId orderDetailId);
    List<ItemDetailDTO> fetchOrderItems(Long orderId);
}
