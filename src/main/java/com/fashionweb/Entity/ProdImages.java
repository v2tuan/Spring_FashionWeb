package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProdImages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name="prodId")
    private Products products;

}
