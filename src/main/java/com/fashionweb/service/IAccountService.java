package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    List<Account>  getAllAccounts();
    Optional<Account> getAccounts(Long accountId);
    Optional<Account> getAccounts(String email);
    <S extends Account> S createAccount(AccountDTO accountDTO);
    <S extends Account> S updateAccount(Long accountId, AccountDTO accountDTO);
    void deleteAccount(long accountId);
    List<CartItem> cart(Long accountId);
    public String login(String identifier, String password) throws Exception;
    public Account getAccountFromToken();
    public Page<AccountResponse> findAllAccounts(String fullname, Boolean enabled, String role, Pageable pageable);
}
