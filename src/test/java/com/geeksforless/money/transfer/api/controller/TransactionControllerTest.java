package com.geeksforless.money.transfer.api.controller;

import com.geeksforless.money.transfer.api.exception.InsufficientFundsException;
import com.geeksforless.money.transfer.api.exception.SameAccountTransferException;
import com.geeksforless.money.transfer.api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.MediaType;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.ID;
import static com.geeksforless.money.transfer.api.fixture.TransactionTestData.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest extends BaseMvcTest {

    private static final String MESSAGE = "$.message";
    private static final String TRANSACTIONS_PATH = "/api/v1/transactions";

    private static final String SAME_ACCOUNT_TRANSFER = "Source and destination accounts are the same: %d".formatted(ID);
    private static final String INSUFFICIENT_FUNDS = "Account: %d doesn't have enough funds to initiate a transfer".formatted(ID);
    private static final String DEADLOCK_DETECTED = "Deadlock detected";

    @MockBean
    private TransactionService transactionService;

    @Test
    void create_whenSameAccountTransfer_shouldReturnBadRequestError() throws Exception {

        when(transactionService.create(any())).thenThrow(new SameAccountTransferException(ID));

        mvc.perform(post(TRANSACTIONS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(TRANSACTION_REQUEST_SAME_ACCOUNT)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(MESSAGE, equalTo(SAME_ACCOUNT_TRANSFER)));

    }

    @Test
    void create_whenInsufficientFunds_shouldReturnBadRequestError() throws Exception {

        when(transactionService.create(any())).thenThrow(new InsufficientFundsException(ID));

        mvc.perform(post(TRANSACTIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(TRANSACTION_REQUEST)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(MESSAGE, equalTo(INSUFFICIENT_FUNDS)));

    }

    @Test
    void create_whenDeadlockDetected_shouldReturnConflictError() throws Exception {
        when(transactionService.create(any())).thenThrow(new CannotAcquireLockException(DEADLOCK_DETECTED));

        mvc.perform(post(TRANSACTIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(TRANSACTION_REQUEST)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath(MESSAGE, equalTo(DEADLOCK_DETECTED)));
    }

    @Test
    void create_shouldCreateTransaction() throws Exception {

        when(transactionService.create(any())).thenReturn(TRANSACTION_RESPONSE);

        mvc.perform(post(TRANSACTIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(TRANSACTION_REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(MAPPER.writeValueAsString(TRANSACTION_RESPONSE)));

    }
}
