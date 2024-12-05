package com.fashionweb.Entity.Embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsId implements Serializable {
    private Long accId;
    private String sizeName;
    private Long prodId;

}
