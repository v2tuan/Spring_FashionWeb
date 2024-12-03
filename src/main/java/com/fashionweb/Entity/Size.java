package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.SizeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Sizes")
public class Size {

    @EmbeddedId
    private SizeId id;

    private String description;
    private Integer quantity;

    @ManyToOne
    @MapsId("prodId")
    @JoinColumn(name = "prodId")
    private Product product;

    @OneToMany(mappedBy = "size")
    private List<CartItem> cartItems;

}

