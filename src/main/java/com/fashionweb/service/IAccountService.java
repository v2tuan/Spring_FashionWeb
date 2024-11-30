package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.accounts.AccountDTO;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    List<Account>  getAllAccounts();
    Optional<Account> getAccounts(Long accountId);
    <S extends Account> S createAccount(AccountDTO accountDTO);
    <S extends Account> S updateAccount(AccountDTO accountDTO);
    void deleteAccount(long accountId);
}
