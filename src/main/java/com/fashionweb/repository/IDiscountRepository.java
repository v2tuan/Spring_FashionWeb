package com.fashionweb.repository;

import com.fashionweb.Entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByStartDate(Date startDate);
    Optional<Discount> findByEndDate(Date endDate);
    Optional<Discount> findByVoucher(String voucher);
}
