package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.OrderItemsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderItems")
public class OrderItem {

    @EmbeddedId
    private OrderItemsId id;

    private Integer quantity;
    private Double price;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    @MapsId("sizeName")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sizeName", referencedColumnName = "sizeName"),
            @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    })
    private Size size;
}


