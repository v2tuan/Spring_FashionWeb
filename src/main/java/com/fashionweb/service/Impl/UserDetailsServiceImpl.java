package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.service.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public String retrieveUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .map(auth -> {
                    Account userEntity = (Account) auth.getPrincipal();
                    return userEntity.getEmail();
                })
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng nhập nên không thể truy xuất danh tính")); // Nếu không tìm thấy email, ném ngoại lệ.
    }

}
