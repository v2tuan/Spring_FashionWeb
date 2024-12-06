package com.fashionweb.api;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.request.VerifyAccountDTO;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.accounts.AccountLoginDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.service.AuthenticationService;
import com.fashionweb.service.Impl.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api")
@RestController
@Slf4j
public class UserAPI {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    IAccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterAccountDTO accountDTO){
            Account account = authenticationService.signup(accountDTO);
//            Account account = userService.createAccount(accountDTO);
            return ResponseEntity.ok(account);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountLoginDTO accountLoginDTO, HttpSession session){
        try{

            String token = accountService.login(accountLoginDTO.getEmail(), accountLoginDTO.getPassword());
            session.setAttribute("authToken", token);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyAccountDTO verifyAccountDTO) {

        authenticationService.verifyUser(verifyAccountDTO);
        return ResponseEntity.ok("Account verified successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    AccountDTO getMyInfo() {

        Account account = accountService.getAccountFromToken();

        return accountMapper.toAccountDTO(account);

//        return "web/my_profile";
    }
}
