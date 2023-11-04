package com.geeksforless.money.transfer.api.mapper;

import com.geeksforless.money.transfer.api.dto.AccountResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    AccountResponse convertToResponse(AccountEntity account);
}
