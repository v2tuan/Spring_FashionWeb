package com.fashionweb.mapper;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.request.accounts.AccountDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    Account toAccount(AccountDTO accountDTO);
    Account toAccount(RegisterAccountDTO registerAccountDTO);
    void updateAccount(@MappingTarget Account account, AccountDTO accountDTO);
    AccountDTO toAccountDTO(Account account);
}
