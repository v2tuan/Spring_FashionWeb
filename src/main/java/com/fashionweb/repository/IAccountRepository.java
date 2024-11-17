package com.fashionweb.repository;

import com.fashionweb.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    List<Account> findByEmail(String email);
}
