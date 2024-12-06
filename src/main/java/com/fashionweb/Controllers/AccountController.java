package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Model.Response;
import com.fashionweb.dto.request.AuthenticationRequestDTO;
import com.fashionweb.dto.request.VerifyAccountDTO;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import com.fashionweb.dto.response.AuthenticationResponse;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.service.AuthenticationService;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private IStorageService storageService;
    @Autowired
    IAccountMapper accountMapper;


    @GetMapping("/me")
    AccountDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));

        return accountMapper.toAccountDTO(account);

//        return "web/my_profile";
    }

    @PostMapping("/signup")
    public ResponseEntity<Account> register(@RequestBody RegisterAccountDTO registerAccountDTO) {
        Account account = authenticationService.signup(registerAccountDTO);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequestDTO);
        return ResponseEntity.ok(authenticationResponse);
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

    @GetMapping
    ResponseEntity<?> getAllAccount(){
        return new ResponseEntity<Response>(new Response(true, "Thành công", accountService.getAllAccounts()), HttpStatus.OK);
    }

    @PostMapping
    Account createAccount(@RequestBody @Valid AccountDTO accountDTO){
        return accountService.createAccount(accountDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateAccount(@PathVariable long id, @ModelAttribute @Valid AccountDTO accountDTO){
        MultipartFile file = accountDTO.getFile();
        if(file.getOriginalFilename() != ""){

        // Tạo tên file duy nhất hoặc từ một ID nào đó
        String fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
        // Lưu file vào hệ thống
        storageService.store(file, fileName);
        accountDTO.setAvatar(fileName);
        }
        accountService.updateAccount(id, accountDTO);
        return new ResponseEntity<Response>(new Response(true, "Thành công", "Update thanh cong"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<Response>(new Response(true, "Thành công", "Xoa thanh cong"), HttpStatus.OK);
    }
}
