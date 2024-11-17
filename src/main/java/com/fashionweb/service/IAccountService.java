package com.fashionweb.service;

import com.fashionweb.Entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    List<Account>  getAllAccounts();
    Optional<Account> getAccounts(Long accountId);
    <S extends Account> S createAccount(S account);
    <S extends Account> S updateAccount(S account);
    void deleteAccount(long accountId);
}
