package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProdImages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdImages_1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name="prodId")
    private Products_1 products;

}
