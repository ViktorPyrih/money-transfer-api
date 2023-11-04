package com.geeksforless.money.transfer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransactionRequest {

    @NotNull
    @Positive
    Long source;
    @NotNull
    @Positive
    Long destination;
    @NotNull
    @Positive
    Double amount;

}
