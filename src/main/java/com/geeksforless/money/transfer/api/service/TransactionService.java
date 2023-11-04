package com.geeksforless.money.transfer.api.service;

import com.geeksforless.money.transfer.api.dto.TransactionRequest;
import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.exception.InsufficientFundsException;
import com.geeksforless.money.transfer.api.exception.SameAccountTransferException;
import com.geeksforless.money.transfer.api.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TransactionResponse create(TransactionRequest request) {

        if (Objects.equals(request.getSource(), request.getDestination())) {
            throw new SameAccountTransferException(request.getSource());
        }

        AccountEntity source = accountService.getById(request.getSource());
        if (source.getBalance() < request.getAmount()) {
            throw new InsufficientFundsException(source.getId());
        }
        AccountEntity destination = accountService.getById(request.getDestination());

        source.withdraw(request.getAmount());
        destination.deposit(request.getAmount());

        accountService.save(source);
        accountService.save(destination);

        return transactionMapper.convertToResponse(source, destination, request.getAmount());

    }
}
