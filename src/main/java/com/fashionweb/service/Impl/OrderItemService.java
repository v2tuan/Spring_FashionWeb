package com.fashionweb.service.Impl;

import com.fashionweb.Entity.OrderItem;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
import com.fashionweb.dto.request.orderAdmin.ItemDetailDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import com.fashionweb.repository.IOrderItemRepository;
import com.fashionweb.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService implements IOrderItemService {
    @Autowired
    private IOrderItemRepository orderItemRepository;
    @Override
    public <S extends OrderItem> S save(S orderDetail) {
        return orderItemRepository.save(orderDetail);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> findById(OrderItemsId orderDetailId) {
        return orderItemRepository.findById(orderDetailId);
    }

    @Override
    public void deleteById(OrderItemsId orderDetailId) {
        orderItemRepository.deleteById(orderDetailId);
    }

    @Override
    public List<ItemDetailDTO> fetchOrderItems(Long orderId) {
        return orderItemRepository.fetchOrderItems(orderId);
    }
}
