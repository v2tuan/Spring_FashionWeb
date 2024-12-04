package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Order;
import com.fashionweb.repository.IOrderRepository;
import com.fashionweb.service.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public <S extends Order> S save(S order) {
        if (order.getOrderId() == null) {
            // Nếu không có OrderId, tạo mới Order
            return orderRepository.save(order);
        } else {
            // Kiểm tra xem Order có tồn tại không
            Optional<Order> orderOptional = orderRepository.findById(order.getOrderId());
            if (orderOptional.isPresent()) {
                // Nếu tồn tại, cập nhật Order
                return orderRepository.save(order);
            } else {
                // Nếu không tồn tại, ném ra ngoại lệ
                throw new EntityNotFoundException("Order with ID " + order.getOrderId() + " does not exist.");
            }
        }
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Optional<Order> findByCreateDate(Date createDate) {
        return orderRepository.findByCreateDate(createDate);
    }

    @Override
    public Optional<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
