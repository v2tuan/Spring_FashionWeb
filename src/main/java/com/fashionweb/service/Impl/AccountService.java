package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public List<Account> getAllAccounts() {
        // Trả về tất cả các tài khoản từ cơ sở dữ liệu
        return iAccountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccounts(Long accountId) {
        // Trả về tài khoản theo ID, nếu không tìm thấy sẽ trả về Optional.empty()
        return iAccountRepository.findById(accountId);
    }

    @Override
    public <S extends Account> S createAccount(S account) {
        // Kiểm tra xem email của tài khoản đã tồn tại trong hệ thống chưa
        if(iAccountRepository.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trên một tài khoản khác.");
        }
        return iAccountRepository.save(account);
    }

    @Override
    public <S extends Account> S updateAccount(S account) {
        // Kiểm tra xem email của tài khoản có bị trùng với tài khoản khác hay không
        List<Account> accounts = iAccountRepository.findByEmail(account.getEmail());
        for (Account existingAccount : accounts) {
            if (existingAccount.getAccountId().equals(account.getAccountId())) {
                continue;
            }
            else{
                throw new RuntimeException("Email đã tồn tại trên một tài khoản khác.");
            }
        }

        return iAccountRepository.save(account);
    }


    @Override
    public void deleteAccount(long accountId) {
        iAccountRepository.deleteById(accountId);
    }
}
