package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Address;
import com.fashionweb.dto.request.address.AddressRequestDTO;

import java.util.List;

public interface IAddressService {
    public List<Address> findAddressByAccount(Account account);

    public Address createAddress(AddressRequestDTO addressRequestDTO);


    public Address updateAddress(Long addressId, AddressRequestDTO addressRequestDTO);


    public boolean deleteAddress(long addressId);
}
