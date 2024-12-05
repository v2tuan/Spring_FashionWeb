package com.fashionweb.mapper;

import com.fashionweb.Entity.Account;
import com.fashionweb.Entity.Address;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import com.fashionweb.dto.request.address.AddressRequestDTO;
import com.fashionweb.dto.response.AddressResone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressMapper {
    Address toAddress(AddressRequestDTO address);
    List<AddressResone> toListAddress(List<Address> address);
    void updateAddress(@MappingTarget Address address, AddressRequestDTO addressRequestDTO);
}
