package com.fashionweb.repository;

import com.fashionweb.Entity.OrderItem;
import com.fashionweb.Entity.Embeddable.OrderItemsId;
import com.fashionweb.dto.request.orderAdmin.ItemDetailDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, OrderItemsId> {
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi")
    Long getTotalProductsSold();

    @Query("""
    SELECT new com.fashionweb.dto.request.orderAdmin.ItemDetailDTO(
        oi.id.prodId,
        COALESCE(pi.imgURL, ''),
        p.prodName,
        oi.id.sizeName,
        oi.price,
        oi.quantity
    )
    FROM OrderItem oi
    JOIN Product p ON p.prodId = oi.id.prodId
    LEFT JOIN ProdImage pi ON pi.product.prodId = oi.id.prodId AND pi.productImageId.stt = 0
    WHERE oi.id.orderId = :orderId
    ORDER BY oi.id.prodId ASC
""")
    List<ItemDetailDTO> fetchOrderItems(@Param("orderId") Long orderId);
}
