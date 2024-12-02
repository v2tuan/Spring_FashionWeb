package com.fashionweb.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCateId;

    private String subCateName;

    @ManyToOne(cascade = CascadeType.ALL)  // Xóa Category, các Subcategory cũng bị xóa
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
}
