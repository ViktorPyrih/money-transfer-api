package com.geeksforless.money.transfer.api.controller;

import com.geeksforless.money.transfer.api.entity.AccountEntity;
import com.geeksforless.money.transfer.api.exception.AccountNotFoundException;
import com.geeksforless.money.transfer.api.mapper.AccountMapper;
import com.geeksforless.money.transfer.api.service.AccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest extends BaseMvcTest {

    private static final String ACCOUNT_NOT_FOUND = "Account: %d not found".formatted(ID);

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    @Test
    void getById_shouldGetAccountById() throws Exception {

        AccountEntity account = createDefaultAccount();
        when(accountService.getById(ID)).thenReturn(account);
        when(accountMapper.convertToResponse(account)).thenReturn(ACCOUNT_RESPONSE);

        mvc.perform(get("/api/v1/accounts/{id}", ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(MAPPER.writeValueAsString(ACCOUNT_RESPONSE)));

    }

    @Test
    void getById_whenNoAccountFound_shouldReturnNotFoundError() throws Exception {

        when(accountService.getById(ID)).thenThrow(new AccountNotFoundException(ID));

        mvc.perform(get("/api/v1/accounts/{id}", ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo(ACCOUNT_NOT_FOUND)));

    }
}
