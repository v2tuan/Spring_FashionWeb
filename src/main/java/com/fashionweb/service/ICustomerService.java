package com.fashionweb.service;

import com.fashionweb.Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    <S extends Customer> S save(S customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long custId);
    void deleteById(Long custId);
    List<Customer> findByCustNameContaining(String custName);
    Optional<Customer> findByCustName(String custName);
}
