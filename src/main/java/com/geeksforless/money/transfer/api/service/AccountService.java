package com.geeksforless.money.transfer.api.service;

import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.exception.AccountNotFoundException;
import com.geeksforless.money.transfer.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountEntity getById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public long save(AccountEntity account) {
        return accountRepository.save(account).getId();
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
