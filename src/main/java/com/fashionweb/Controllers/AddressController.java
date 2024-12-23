package com.fashionweb.Controllers;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Address;
import com.fashionweb.Model.Response;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.address.AddressRequestDTO;
import com.fashionweb.mapper.IAddressMapper;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IAddressMapper addressMapper;

    @GetMapping
    @ResponseBody
    ResponseEntity<?> getAllAddress(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));
        return new ResponseEntity<Response>(new Response(true, "Thành công", addressMapper.toListAddress(addressService.findAddressByAccount(account))), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    Address createAddress(@RequestBody AddressRequestDTO addressRequestDTO){
        return addressService.createAddress(addressRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseBody
    ResponseEntity<?> updateAddress(@PathVariable long id, @RequestBody @Valid AddressRequestDTO addressRequestDTO){
        addressService.updateAddress(id, addressRequestDTO);
        return new ResponseEntity<Response>(new Response(true, "Thành công", "Update thanh cong"), HttpStatus.OK);
    }

    @GetMapping("/managerAddress")
    String managerAddress(Model model){
        return "web/manager_address";
    }

}
