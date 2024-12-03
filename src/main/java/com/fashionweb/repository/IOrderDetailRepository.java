package com.fashionweb.repository;

import com.fashionweb.Entity.OrderItem;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderItem, OrderItemsId> {
}
