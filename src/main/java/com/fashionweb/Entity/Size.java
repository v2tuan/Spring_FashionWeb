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
    @JoinColumn(name = "prodId", referencedColumnName = "prodId", insertable = false, updatable = false)
    private Product product;  // Liên kết với Product

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;  // Liên kết với CartItem


}

