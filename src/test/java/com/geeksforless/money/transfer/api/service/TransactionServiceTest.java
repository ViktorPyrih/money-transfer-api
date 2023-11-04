package com.geeksforless.money.transfer.api.service;

import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.exception.InsufficientFundsException;
import com.geeksforless.money.transfer.api.exception.SameAccountTransferException;
import com.geeksforless.money.transfer.api.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.createAccount;
import static com.geeksforless.money.transfer.api.fixture.TransactionTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class TransactionServiceTest {

    private static final String SAME_ACCOUNT_TRANSFER = "Source and destination accounts are the same: %d".formatted(SOURCE_ID);
    private static final String INSUFFICIENT_FUNDS = "Account: %d doesn't have enough funds to initiate a transfer".formatted(SOURCE_ID);

    private static final double BALANCE_ZERO = 0.0;
    private static final double SOURCE_NEW_BALANCE = 900.0;
    private static final double DESTINATION_NEW_BALANCE = 1100.0;

    @InjectMocks
    private TransactionService target;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    void create_whenSourceAccountIsEqualToDestinationAccount_shouldThrowSameAccountTransferException() {

        assertThatExceptionOfType(SameAccountTransferException.class)
                .isThrownBy(() -> target.create(TRANSACTION_REQUEST_SAME_ACCOUNT))
                .withMessage(SAME_ACCOUNT_TRANSFER);

    }

    @Test
    void create_whenSourceBalanceIsLessThanRequestedAmount_shouldThrowInsufficientFundsException() {

        when(accountService.getById(SOURCE_ID)).thenReturn(createAccount(SOURCE_NAME, SOURCE_SURNAME, BALANCE_ZERO));

        assertThatExceptionOfType(InsufficientFundsException.class)
                .isThrownBy(() -> target.create(TRANSACTION_REQUEST))
                .withMessage(INSUFFICIENT_FUNDS);

    }

    @Test
    void create_shouldCreateTransaction() {

        AccountEntity source = createAccount(SOURCE_NAME, SOURCE_SURNAME);
        when(accountService.getById(SOURCE_ID)).thenReturn(source);
        AccountEntity destination = createAccount(DESTINATION_NAME, DESTINATION_SURNAME);
        when(accountService.getById(DESTINATION_ID)).thenReturn(destination);
        when(transactionMapper.convertToResponse(source, destination, AMOUNT)).thenReturn(TRANSACTION_RESPONSE);

        TransactionResponse actual = target.create(TRANSACTION_REQUEST);

        assertThat(actual).isEqualTo(TRANSACTION_RESPONSE);
        assertThat(source.getBalance()).isEqualTo(SOURCE_NEW_BALANCE);
        assertThat(destination.getBalance()).isEqualTo(DESTINATION_NEW_BALANCE);

        verify(accountService).save(source);
        verify(accountService).save(destination);

    }
}
