package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsId id;

    private Double price;
    private Integer quantity;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("prodId")
    @JoinColumn(name = "prodId", nullable = true)  // Cho phép null khi xóa Product
    private Product product;

    @ManyToOne
    @MapsId("size")
    @JoinColumn(name = "size", nullable = true)  // Cho phép null khi xóa Size
    private Size size;
}
