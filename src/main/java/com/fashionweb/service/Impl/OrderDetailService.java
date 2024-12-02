package com.fashionweb.service.Impl;

import com.fashionweb.Entity.OrderDetail;
import com.fashionweb.Entity.OrderDetailsId;
import com.fashionweb.repository.IOrderDetailRepository;
import com.fashionweb.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Override
    public <S extends OrderDetail> S save(S orderDetail) {
        // Kiểm tra nếu ID bị null
        if (orderDetail.getId() == null) {
            throw new IllegalArgumentException("OrderDetailsId must not be null.");
        }

        // Kiểm tra sự tồn tại của OrderDetail theo khóa chính
        Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findById(orderDetail.getId());

        if (existingOrderDetail.isPresent()) {
            // Nếu đã tồn tại, cập nhật bản ghi
            return orderDetailRepository.save(orderDetail);
        } else {
            // Nếu chưa tồn tại, lưu bản ghi mới
            return orderDetailRepository.save(orderDetail);
        }
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(OrderDetailsId orderDetailId) {
        return orderDetailRepository.findById(orderDetailId);
    }

    @Override
    public void deleteById(OrderDetailsId orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }
}
