package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.service.Impl.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @PostMapping
    Account createAccount(@RequestBody @Valid Account account){
        return accountService.save(account);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateAccount(@PathVariable long id, @RequestBody @Valid Account account){
        Optional<Account> optionalAccount = accountService.getAccounts(id);
        if(optionalAccount.isPresent()){
            Account updatedAccount = optionalAccount.get();
            // Chỉ sao chép các thuộc tính không bao gồm 'id'
            updatedAccount.setEmail(account.getEmail());
            updatedAccount.setPassword(account.getPassword());
            updatedAccount.setProfilePic(account.getProfilePic());
            updatedAccount.setRole(account.getRole());
            updatedAccount.setStatus(account.getStatus());
            accountService.save(updatedAccount);
            return new ResponseEntity<String>("Update thanh cong",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Id khong ton tai",HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/{id}")
    String deleteAccount(@PathVariable long id){
        accountService.deleteAccount(id);
        return "Xoa thanh cong";
    }
}
