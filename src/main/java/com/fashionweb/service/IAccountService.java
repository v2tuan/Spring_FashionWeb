package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.dto.request.accounts.AccountDTO;

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
}
