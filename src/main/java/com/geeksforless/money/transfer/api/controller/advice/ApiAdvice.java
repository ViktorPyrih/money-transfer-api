package com.geeksforless.money.transfer.api.controller.advice;

import com.geeksforless.money.transfer.api.exception.AccountNotFoundException;
import com.geeksforless.money.transfer.api.exception.InsufficientFundsException;
import com.geeksforless.money.transfer.api.exception.SameAccountTransferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Clock;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice {

    private final Clock clock;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InsufficientFundsException.class, SameAccountTransferException.class})
    public ApiError handleInsufficientFundsException(RuntimeException exception, WebRequest webRequest) {
        log.warn("Insufficient funds for request: {}", webRequest, exception);
        return buildApiError(exception, webRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ApiError handleAccountNotFoundException(AccountNotFoundException exception, WebRequest webRequest) {
        log.warn("Account not found for request: {}", webRequest, exception);
        return buildApiError(exception, webRequest);
    }

    private ApiError buildApiError(Exception exception, WebRequest webRequest) {
        return ApiError.builder()
                .incidentId(UUID.randomUUID().toString())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .timestamp(clock.instant())
                .build();
    }
}
