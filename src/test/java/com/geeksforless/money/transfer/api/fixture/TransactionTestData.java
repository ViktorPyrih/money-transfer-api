package com.geeksforless.money.transfer.api.fixture;

import com.geeksforless.money.transfer.api.dto.TransactionRequest;
import com.geeksforless.money.transfer.api.dto.TransactionResponse;

public interface TransactionTestData {

    long SOURCE_ID = 123;
    long DESTINATION_ID = 456;

    String SOURCE_NAME = "Any source name";
    String SOURCE_SURNAME = "Any source surname";

    String DESTINATION_NAME = "Any destination name";
    String DESTINATION_SURNAME = "Any destination surname";

    double AMOUNT = 100.0;

    TransactionRequest TRANSACTION_REQUEST_SAME_ACCOUNT = TransactionRequest.builder()
            .source(SOURCE_ID)
            .destination(SOURCE_ID)
            .amount(AMOUNT)
            .build();

    TransactionRequest TRANSACTION_REQUEST = TransactionRequest.builder()
            .source(SOURCE_ID)
            .destination(DESTINATION_ID)
            .amount(AMOUNT)
            .build();

    TransactionResponse TRANSACTION_RESPONSE = TransactionResponse.builder()
            .source(TransactionResponse.Account.builder()
                    .name(SOURCE_NAME)
                    .surname(SOURCE_SURNAME)
                    .build())
            .destination(TransactionResponse.Account.builder()
                    .name(DESTINATION_NAME)
                    .surname(DESTINATION_SURNAME)
                    .build())
            .amount(AMOUNT)
            .build();

    static TransactionRequest createRequest(long source, long destination) {
        return TransactionRequest.builder()
                .source(source)
                .destination(destination)
                .amount(AMOUNT)
                .build();
    }
}
