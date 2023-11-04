package com.geeksforless.money.transfer.api.exception;

public class SameAccountTransferException extends RuntimeException {

    private static final String MESSAGE = "Source and destination accounts are the same: %d";

    public SameAccountTransferException(long accountId) {
        super(MESSAGE.formatted(accountId));
    }
}
