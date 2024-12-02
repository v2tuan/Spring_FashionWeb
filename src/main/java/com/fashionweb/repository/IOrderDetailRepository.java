package com.fashionweb.repository;

import com.fashionweb.Entity.OrderDetail;
import com.fashionweb.Entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailsId> {
}
