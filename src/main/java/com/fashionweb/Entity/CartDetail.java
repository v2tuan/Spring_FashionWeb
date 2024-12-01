package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CartDetails")
public class CartDetail {
    @EmbeddedId
    private CartDetailsId id;

    private Integer quantity;
    private Double totalPerProduct;

    @ManyToOne
    @MapsId("size")
    @JoinColumn(name = "size", nullable = false)
    private Size size;

    @ManyToOne
    @MapsId("prodId")
    @JoinColumn(name = "prodId", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @ManyToOne
    @MapsId("custId")
    @JoinColumn(name = "custId", nullable = false)
    private Customer customer;
}
