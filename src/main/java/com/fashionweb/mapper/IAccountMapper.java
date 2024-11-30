package com.fashionweb.mapper;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.accounts.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    Account toAccount(AccountDTO accountDTO);
    void updateAccount(@MappingTarget Account account, AccountDTO accountDTO);
}
