package com.geeksforless.money.transfer.api.fixture;

import com.geeksforless.money.transfer.api.dto.AccountResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;

public interface AccountTestData {

    long ID = 123;
    String NAME = "Any name";
    String SURNAME = "Any surname";
    double BALANCE = 1000.0;

    AccountResponse ACCOUNT_RESPONSE = AccountResponse.builder()
            .name(NAME)
            .surname(SURNAME)
            .balance(BALANCE)
            .build();

    static AccountEntity createDefaultAccount() {
        return createAccount(NAME, SURNAME);
    }

    static AccountEntity createAccount(String name) {
        return createAccount(name, name, BALANCE);
    }

    static AccountEntity createAccount(String name, String surname) {
        return createAccount(name, surname, BALANCE);
    }

    static AccountEntity createAccount(String name, String surname, double balance) {
        AccountEntity account = new AccountEntity();
        account.setId(ID);
        account.setName(name);
        account.setSurname(surname);
        account.setBalance(balance);
        return account;
    }
}
