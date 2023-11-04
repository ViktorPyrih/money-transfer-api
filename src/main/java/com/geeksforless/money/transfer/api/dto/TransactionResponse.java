package com.geeksforless.money.transfer.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransactionResponse {

    Account source;
    Account destination;
    double amount;

    @Value
    @Builder
    public static class Account {
        String name;
        String surname;
    }

}
