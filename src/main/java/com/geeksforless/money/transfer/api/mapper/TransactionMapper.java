package com.geeksforless.money.transfer.api.mapper;

import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransactionMapper {

    TransactionResponse convertToResponse(AccountEntity source, AccountEntity destination, double amount);
}
