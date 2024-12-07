package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.CartItem;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.response.AccountResponse;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.repository.ICartItemRepository;
import com.fashionweb.service.IAccountService;
import com.fashionweb.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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
        account.setCreateDate(LocalDate.now());
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

    @Override
    public String login(String identifier, String password) throws Exception {
        Optional<Account> optionalUserEntity = iAccountRepository.findAccountByEmail(identifier);

        if (optionalUserEntity.isEmpty()) {
            throw new RuntimeException("Invalid Email");
        }

        // Bước 3: Lấy tài khoản người dùng hiện tại từ đối tượng Optional.
        Account account = optionalUserEntity.get();

        // Bước 4: Kiểm tra mật khẩu người dùng nhập vào có khớp với mật khẩu đã được mã hóa trong cơ sở dữ liệu hay không.
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Wrong Password"); // Nếu không khớp, ném ngoại lệ "Mật khẩu sai".
        }

        // Bước 5: Tạo đối tượng authenticationToken để chứa thông tin xác thực, bao gồm identifier (email/username) và các quyền của người dùng.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                identifier, password,
                account.getAuthorities() // Các quyền hạn của người dùng.
        );

        // Bước 6: Xác thực thông qua authenticationManager để kiểm tra tính hợp lệ của người dùng.
        authenticationManager.authenticate(authenticationToken);

        // Bước 7: Tạo và trả về JWT token cho người dùng sau khi xác thực thành công.
        return jwtTokenUtil.generateToken(account);
    }

    @Override
    public Account getAccountFromToken() {
        String email = userDetailsService.retrieveUserEmail();
        return getAccounts(email).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng"));
    }

    @Override
    public Page<AccountResponse> findAllAccounts(String fullname, Boolean enabled, Role role, Pageable pageable) {
        return iAccountRepository.findAllAccount(fullname, enabled, role,pageable);
    }
}
