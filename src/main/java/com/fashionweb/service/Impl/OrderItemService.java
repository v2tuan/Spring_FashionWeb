package com.fashionweb.service.Impl;

import com.fashionweb.Entity.OrderItem;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
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
        // Kiểm tra nếu ID bị null
        if (orderDetail.getId() == null) {
            throw new IllegalArgumentException("OrderDetailsId must not be null.");
        }

        // Kiểm tra sự tồn tại của OrderDetail theo khóa chính
        Optional<OrderItem> existingOrderDetail = orderItemRepository.findById(orderDetail.getId());

        if (existingOrderDetail.isPresent()) {
            // Nếu đã tồn tại, cập nhật bản ghi
            return orderItemRepository.save(orderDetail);
        } else {
            // Nếu chưa tồn tại, lưu bản ghi mới
            return orderItemRepository.save(orderDetail);
        }
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
}
