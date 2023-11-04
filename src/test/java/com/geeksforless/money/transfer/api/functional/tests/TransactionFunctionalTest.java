package com.geeksforless.money.transfer.api.functional.tests;

import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.allOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TransactionFunctionalTest extends BaseFunctionalTest {

    private static final long SOURCE_ACCOUNT_ID = 1;
    private static final long DESTINATION_ACCOUNT_ID = 2;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void create_whenCreatingTransactionsConcurrently_shouldReturnCorrectBalance() {
        CompletableFuture<?>[] tasks = run10Times(() -> runAsync(this::createTransaction), CompletableFuture[]::new);
        allOf(tasks).join();

        assertAccountBalance(SOURCE_ACCOUNT_ID, 0.0);
        assertAccountBalance(DESTINATION_ACCOUNT_ID, 2000.0);
    }

    private void createTransaction() {
        transactionSteps.create(SOURCE_ACCOUNT_ID, DESTINATION_ACCOUNT_ID);
    }

    private void assertAccountBalance(long id, double balance) {
        assertThat(accountRepository.findById(id))
                .isPresent().get()
                .extracting(AccountEntity::getBalance)
                .isEqualTo(balance);
    }
}
