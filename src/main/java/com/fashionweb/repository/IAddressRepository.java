package com.fashionweb.repository;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByAccount(Account account);
}
