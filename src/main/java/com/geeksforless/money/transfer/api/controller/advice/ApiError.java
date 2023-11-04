package com.geeksforless.money.transfer.api.controller.advice;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class ApiError {

    String incidentId;
    String message;
    String details;
    Instant timestamp;

}
