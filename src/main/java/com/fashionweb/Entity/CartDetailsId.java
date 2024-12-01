package com.fashionweb.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsId implements Serializable {
    private String size;
    private Long prodId;
    private Long cartId;
    private Long custId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetailsId that = (CartDetailsId) o;
        return Objects.equals(size, that.size) &&
                Objects.equals(prodId, that.prodId) &&
                Objects.equals(cartId, that.cartId) &&
                Objects.equals(custId, that.custId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, prodId, cartId, custId);
    }
}
