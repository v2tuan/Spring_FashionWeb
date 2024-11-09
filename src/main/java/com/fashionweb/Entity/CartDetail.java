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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartDetailId;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "size")
    private Size size;

    private Integer quantity;
    private Double totalPerProduct;
}
