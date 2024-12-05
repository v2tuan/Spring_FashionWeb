package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Address;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.address.AddressRequestDTO;
import com.fashionweb.mapper.IAddressMapper;
import com.fashionweb.repository.IAddressRepository;
import com.fashionweb.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    @Autowired
    IAddressRepository addressRepository;
    @Autowired
    IAddressMapper addressMapper;
    @Autowired
    AccountService accountService;

    @Override
    public List<Address> findAddressByAccount(Account account){
        return addressRepository.findAddressByAccount(account);
    }

    @Override
    public Address createAddress(AddressRequestDTO addressRequestDTO) {
        Address address = addressMapper.toAddress(addressRequestDTO);
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        Account account = accountService.getAccounts(email).orElseThrow(
                () -> new RuntimeException("Không tìm thấy người dùng"));

        address.setAccount(account);
        return addressRepository.save(address);
    }


    @Override
    public Address updateAddress(Long addressId, AddressRequestDTO addressRequestDTO) {
        Address address = addressRepository.findById(addressId).orElse(null);
        addressMapper.updateAddress(address, addressRequestDTO);

        return addressRepository.save(address);
    }


    @Override
    public boolean deleteAddress(long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }
}
