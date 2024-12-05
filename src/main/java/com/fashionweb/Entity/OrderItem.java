package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.OrderItemsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @EmbeddedId
    private OrderItemsId id;

    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "orderId", insertable=false, updatable=false)
    private Order order;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sizeName", referencedColumnName = "sizeName", insertable = false, updatable = false),
            @JoinColumn(name = "prodId", referencedColumnName = "prodId", insertable = false, updatable = false)
    })
    private Size size;
}
