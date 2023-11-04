package com.geeksforless.money.transfer.api.runner;

import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Creates initial accounts for the service
* */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultCommandLineRunner implements CommandLineRunner {

    private static final double DEFAULT_BALANCE = 1000;

    private final AccountService accountService;

    @Override
    @Transactional
    public void run(String... args) {
        accountService.deleteAll();
        long accountId1 = accountService.save(createAccount("Cristiano", "Ronaldo"));
        long accountId2 = accountService.save(createAccount("Lionel", "Messi"));
        log.info("Created accounts with ids: {}, {}", accountId1, accountId2);
    }

    private AccountEntity createAccount(String name, String surname) {
        AccountEntity account = new AccountEntity();
        account.setName(name);
        account.setSurname(surname);
        account.setBalance(DEFAULT_BALANCE);
        return account;
    }
}
