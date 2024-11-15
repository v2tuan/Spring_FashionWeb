package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    private String size;

    @ManyToOne
    @JoinColumn(name = "prodId")
    private Product product;

    @OneToMany(mappedBy = "size")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "size")
    private List<CartDetail> cartDetails;
}

