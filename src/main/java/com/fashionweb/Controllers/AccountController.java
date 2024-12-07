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
import com.fashionweb.service.Impl.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
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
    @Autowired
    AddressService addressService;




//    @PostMapping("/signup")
//    public ResponseEntity<Account> register(@RequestBody RegisterAccountDTO registerAccountDTO) {
//        Account account = authenticationService.signup(registerAccountDTO);
//        return ResponseEntity.ok(account);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
//        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequestDTO);
//        return ResponseEntity.ok(authenticationResponse);
//    }

    @GetMapping
    ResponseEntity<?> getAllAccount(){
        return new ResponseEntity<Response>(new Response(true, "Thành công", accountService.getAllAccounts()), HttpStatus.OK);
    }

    @PostMapping
    Account createAccount(@RequestBody @Valid AccountDTO accountDTO){
        return accountService.createAccount(accountDTO);
    }

    @PutMapping
    ResponseEntity<?> updateAccount(@ModelAttribute @Valid AccountDTO accountDTO){
        Account account = accountService.getAccountFromToken();
        Long id = account.getAccId();
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


    @GetMapping("/profile")
    String profile(){
        return "web/my_profile";
    }

    @GetMapping("/managerAddress")
    String managerAddress(Model model){
        return "web/manager_address";
    }

    @PostMapping("/deladdr/{id}")
    String deleteAccount(@PathVariable long id, Model model){
        if (addressService.deleteAddress(id)) {
            model.addAttribute("errorMessage", "Xóa thành công");
        }
        return "web/manager_address";
    }

}
