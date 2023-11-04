package com.geeksforless.money.transfer.api.service;

import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.exception.AccountNotFoundException;
import com.geeksforless.money.transfer.api.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.ID;
import static com.geeksforless.money.transfer.api.fixture.AccountTestData.createDefaultAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class AccountServiceTest {

    private static final String ACCOUNT_NOT_FOUND = "Account: %d not found".formatted(ID);

    @InjectMocks
    private AccountService target;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void getById_shouldGetAccountById() {

        AccountEntity expected = createDefaultAccount();
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        AccountEntity actual = target.getById(ID);

        assertThat(actual).isEqualTo(expected);
        verify(accountRepository).findById(ID);

    }

    @Test
    void getById_whenAccountNotFound_shouldThrowAccountNotFoundException() {

        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> target.getById(ID))
                .withMessage(ACCOUNT_NOT_FOUND);

    }

    @Test
    void save_shouldSaveAccount() {

        AccountEntity account = createDefaultAccount();
        when(accountRepository.save(any())).thenReturn(account);

        long id = target.save(account);

        assertThat(id).isEqualTo(ID);
        verify(accountRepository).save(account);

    }

    @Test
    void deleteAll_shouldDeleteAllAccounts() {

        target.deleteAll();

        verify(accountRepository).deleteAll();

    }
}
