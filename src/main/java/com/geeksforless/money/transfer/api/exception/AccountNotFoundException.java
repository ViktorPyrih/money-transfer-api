package com.geeksforless.money.transfer.api.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Account: %d not found";

    public AccountNotFoundException(long accountId) {
        super(MESSAGE.formatted(accountId));
    }
}
