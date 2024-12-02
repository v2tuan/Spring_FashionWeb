package com.fashionweb.service;

import com.fashionweb.Entity.Discount;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    <S extends Discount> S save(S entity);
    List<Discount> findAll();
    Optional<Discount> findById(Long id);
    void deleteById(Long id);
    Optional<Discount> findByStartDate(Date startDate);
    Optional<Discount> findByEndDate(Date endDate);
}
