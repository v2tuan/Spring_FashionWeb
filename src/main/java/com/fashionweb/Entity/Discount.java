    package com.fashionweb.Entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.Date;
    import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "Discounts")
    public class Discount {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long discountId;

        private String voucher;
        private String description;
        private Double discountPercentage;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate createDate;

        @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Order> orders;
    }
