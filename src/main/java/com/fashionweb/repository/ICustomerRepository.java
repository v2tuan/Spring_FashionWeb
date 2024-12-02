package com.fashionweb.repository;

import com.fashionweb.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCustNameContaining(String custName);
    Optional<Customer> findByCustName(String custName);
}
