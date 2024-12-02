package com.fashionweb.Entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String imageURL;

    private String stt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodId", nullable = true)  // Cho phép null khi xóa Product
    private Product product;
}
