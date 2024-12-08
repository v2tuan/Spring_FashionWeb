package com.fashionweb.repository;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Order;
import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.OrderDTO;
import com.fashionweb.dto.request.orderAdmin.OrderAdminDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCreateDate(Date createDate);
    Optional<Order> findByStatus(String status);
    List<Order> findAllByDiscountDiscountId(Long discountId);
    Page<Order> findByAccount(Account account, Pageable pageable);
    @Query("SELECT SUM(o.total) FROM Order o")

    Double getTotalRevenue();
    long count();

    @Query("""
        SELECT new com.fashionweb.dto.request.orderAdmin.OrderAdminDTO(
            o.orderId,
            o.createDate,
            o.total,
            o.status,
            o.account.accId,
            o.discount.discountId,
            o.address.id
        )
        FROM Order o
        WHERE (:orderDate IS NULL OR o.createDate = :orderDate)
        AND (:orderStatus IS NULL OR o.status = :orderStatus)
        ORDER BY o.createDate DESC
    """)
    Page<OrderAdminDTO> fetchOrderListPageable(
            @Param("orderDate") LocalDate orderDate,
            @Param("orderStatus") OrderStatus orderStatus,
            Pageable pageable);

    @Query("""
        SELECT new com.fashionweb.dto.request.orderAdmin.OrderAdminDTO(
            o.orderId,
            o.createDate,
            o.total,
            o.status,
            o.account.accId,
            o.discount.discountId,
            o.address.id
        )
        FROM Order o
        WHERE (:accId IS NULL OR o.account.accId = :accId)
        AND (:orderDate IS NULL OR o.createDate = :orderDate)
        AND (:orderStatus IS NULL OR o.status = :orderStatus)
    """)
    Page<OrderAdminDTO> fetchOrderListPageableById(
            @Param("accId") Long accId,
            @Param("orderDate") LocalDate orderDate,
            @Param("orderStatus") OrderStatus orderStatus,
            Pageable pageable);

    @Query("""
    SELECT new com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO(
        o.account.accId,
        o.orderId,
        o.createDate,
        c.fullname,
        c.email,
        c.phone,
        a.addr,
        a.fullName,
        a.phone,
        o.total,
        o.status
    )
    FROM Order o
    JOIN o.account c
    JOIN o.address a
    WHERE o.orderId = :orderId
""")
    OrderDetailAdminDTO fetchOrderDetail(@Param("orderId") Long orderId);
}
