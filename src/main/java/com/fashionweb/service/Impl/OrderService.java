package com.fashionweb.service.Impl;

import com.fashionweb.Entity.*;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.repository.*;
import com.fashionweb.service.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    private IDiscountRepository discountRepository;

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

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        // Lấy thông tin Account và Address
        Account account = accountRepository.findById(orderDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Địa chỉ không tồn tại!"));

        Discount discount = null;
        if (orderDTO.getDiscountId() != null) {
            discount = discountRepository.findById(orderDTO.getDiscountId())
                    .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại!"));
        }
        // Tạo Order
        Order order = new Order();
        order.setAccount(account);
        order.setAddress(address);
        order.setDiscount(discount);
        order.setCreateDate(LocalDate.now());
        order.setStatus(OrderStatus.NEW); // Trạng thái mặc định
        order.setTotal(orderDTO.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        if (discount != null && discount.getDiscountPercentage() != null) {
            order.setTotal(order.getTotal() - (order.getTotal() * discount.getDiscountPercentage() / 100));
        }

        Order savedOrder = orderRepository.save(order);

        // Lưu OrderItem
        orderDTO.getItems().forEach(itemDTO -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(new OrderItemsId(savedOrder.getOrderId(), itemDTO.getSizeName(), itemDTO.getProdId()));
            orderItem.setOrder(savedOrder);
            orderItem.setPrice(itemDTO.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());

            orderItemRepository.save(orderItem);
        });

        return savedOrder;
    }
}
