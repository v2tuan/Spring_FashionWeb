package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.repository.ICartItemRepository;
import com.fashionweb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private IAccountMapper accountMapper;
    @Autowired
    private ICartItemRepository cartDetailRepos;

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
    public Optional<Account> getAccounts(String email) {
        // Trả về tài khoản theo ID, nếu không tìm thấy sẽ trả về Optional.empty()
        return iAccountRepository.findAccountByEmail(email);
    }

    @Override
    public Account createAccount(AccountDTO accountDTO) {
        // Kiểm tra xem email của tài khoản đã tồn tại trong hệ thống chưa
        if(iAccountRepository.existsByEmail(accountDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trên một tài khoản khác.");
        }
        Account account = accountMapper.toAccount(accountDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(true);
        account.setRole(Role.USER);
        return iAccountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, AccountDTO accountDTO) {
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra xem email của tài khoản có bị trùng với tài khoản khác hay không
        List<Account> accounts = iAccountRepository.findByEmail(accountDTO.getEmail());

        if (accounts.size() > 1 || (accounts.size() == 1 && !accounts.get(0).getAccId().equals(account.getAccId()))) {
            throw new RuntimeException("Email đã tồn tại trên một tài khoản khác...");
        }
        // lưu lại tên ảnh cũ
        String avater = account.getAvatar();
        accountMapper.updateAccount(account, accountDTO);
        if(accountDTO.getAvatar() == null) {
            account.setAvatar(avater);
        }
        return iAccountRepository.save(account);
    }


    @Override
    public void deleteAccount(long accountId) {
        iAccountRepository.deleteById(accountId);
    }

    @Override
    public List<CartItem> cart(Long accountId) {
        return cartDetailRepos.findAllByAccountAccId(accountId);
    }
}
