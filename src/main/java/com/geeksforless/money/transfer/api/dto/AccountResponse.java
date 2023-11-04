package com.geeksforless.money.transfer.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountResponse {

    String name;
    String surname;
    double balance;

}
