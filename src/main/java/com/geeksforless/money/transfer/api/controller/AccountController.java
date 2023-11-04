package com.geeksforless.money.transfer.api.controller;

import com.geeksforless.money.transfer.api.dto.AccountResponse;
import com.geeksforless.money.transfer.api.mapper.AccountMapper;
import com.geeksforless.money.transfer.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable long id) {
        return accountMapper.convertToResponse(accountService.getById(id));
    }
}
