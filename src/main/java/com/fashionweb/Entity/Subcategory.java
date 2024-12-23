    package com.fashionweb.Entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "Subcategories")
    public class Subcategory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment behavior
        private Long subCateId;  // This is the primary key

        private String subCateName;
        private String description;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "categoryId", nullable = false)
        private Category category;

        @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Product> products;
    }

