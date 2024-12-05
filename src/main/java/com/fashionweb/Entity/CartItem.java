package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.CartItemsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CartItems")
public class CartItem {
    @EmbeddedId
    private CartItemsId id;

    private Integer quantity;
    private Double price;
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "accId", insertable = false, updatable = false)
    private Account account;

//    @ManyToOne
//    @MapsId("prodId")
//    @JoinColumn(name = "prodId", insertable = false, updatable = false)
//    private Product product;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sizeName", referencedColumnName = "sizeName", insertable = false, updatable = false),
            @JoinColumn(name = "prodId", referencedColumnName = "prodId", insertable = false, updatable = false)
    })
    private Size size;

}


