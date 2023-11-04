package com.geeksforless.money.transfer.api.exception;

public class InsufficientFundsException extends RuntimeException {

    private static final String MESSAGE = "Account: %d doesn't have enough funds to initiate a transfer";

    public InsufficientFundsException(long accountId) {
        super(MESSAGE.formatted(accountId));
    }
}
