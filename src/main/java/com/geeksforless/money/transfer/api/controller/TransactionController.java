package com.geeksforless.money.transfer.api.controller;

import com.geeksforless.money.transfer.api.dto.TransactionRequest;
import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import com.geeksforless.money.transfer.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@RequestBody @Valid TransactionRequest request) {
        return transactionService.create(request);
    }
}
