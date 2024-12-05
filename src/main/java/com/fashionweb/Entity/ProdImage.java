package com.fashionweb.Entity;

import com.fashionweb.Entity.Embeddable.ProductImagesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProdImages")
public class ProdImage {
    @EmbeddedId
    private ProductImagesId productImageId;

    @ManyToOne
    @JoinColumn(name = "prodId", insertable=false, updatable=false)
    private Product product;
}
