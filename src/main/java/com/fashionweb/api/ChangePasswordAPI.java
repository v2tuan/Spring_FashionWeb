package com.fashionweb.api;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.request.accounts.ChangePasswordDTO;
import com.fashionweb.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pass")
@RestController
@Slf4j
public class ChangePasswordAPI {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sendcodechangepassword")
    public ResponseEntity<?> sendCodeChangePassword(){
        Account account = authenticationService.sendCodechangePassword();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        Account account = authenticationService.changePassword(changePasswordDTO);
        return ResponseEntity.ok(account);
    }
}
