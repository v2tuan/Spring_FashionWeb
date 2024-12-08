package com.fashionweb.repository;

import com.fashionweb.Entity.Account;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    Optional<Account> findAccountByEmail(String email);
    List<Account> findByEmail(String email);

    @Query("SELECT new com.fashionweb.dto.response.AccountResponse(a.accId, a.email, a.avatar, a.fullname, a.enabled, a.gender, a.createDate, a.role) " +
            "FROM Account a " +
            "WHERE (:fullname IS NULL OR a.fullname LIKE %:fullname%) " +
            "AND (:enabled IS NULL OR a.enabled = :enabled) " +
            "AND (:role IS NULL OR a.role = :role) ")
//            "ORDER BY a.createDate DESC")
    Page<AccountResponse> findAllAccount(@Param("fullname") String fullname,
                                         @Param("enabled") Boolean enabled,
                                         @Param("role") Role role,
                                         Pageable pageable);
}
