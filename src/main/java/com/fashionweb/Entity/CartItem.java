package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.CartItemsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CartItems")
public class CartItem {
    @EmbeddedId
    private CartItemsId id;

    private Integer quantity;
    private LocalDateTime createDate;

    @MapsId("accId")
    @ManyToOne
    @JoinColumn(name = "accId", referencedColumnName = "accId")
    private Account account;

    @MapsId("prodId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sizeName", referencedColumnName = "sizeName"),
            @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    })
    private Size size;

}


