package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Model.Response;
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
    ResponseEntity<?> getAllAccount(){
        return new ResponseEntity<Response>(new Response(true, "Thành công", accountService.getAllAccounts()), HttpStatus.OK);
    }

    @PostMapping
    Account createAccount(@RequestBody @Valid Account account){
        return accountService.createAccount(account);
    }

    @PutMapping
    ResponseEntity<?> updateAccount(@RequestBody @Valid Account account){
        accountService.updateAccount(account);
        return new ResponseEntity<Response>(new Response(true, "Thành công", "Update thanh cong"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<Response>(new Response(true, "Thành công", "Xoa thanh cong"), HttpStatus.OK);
    }
}
