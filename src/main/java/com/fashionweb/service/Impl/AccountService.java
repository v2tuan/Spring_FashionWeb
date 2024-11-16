package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return iAccountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccounts(Long accountId) {
        return iAccountRepository.findById(accountId);
    }

    @Override
    public <S extends Account> S save(S account) {
        return iAccountRepository.save(account);
    }

    @Override
    public void deleteAccount(long accountId) {
        iAccountRepository.deleteById(accountId);
    }
}
