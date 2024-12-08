package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Order;
import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.dto.request.orderAdmin.OrderAdminDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
    Order createOrder(OrderDTO orderDTO);
    Page<OrderAdminDTO> findAllOrderListPageable(LocalDate orderDate, OrderStatus orderStatus, Pageable pageable);
    Page<OrderAdminDTO> findAllOrderListPageableById(Long accId, LocalDate orderDate, OrderStatus orderStatus, Pageable pageable);
    Page<Order> findOrdersByAccount(Account account, Pageable pageable);
    OrderDetailAdminDTO fetchOrderDetail(Long orderId);
}
