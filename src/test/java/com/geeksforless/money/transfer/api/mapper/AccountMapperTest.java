package com.geeksforless.money.transfer.api.mapper;

import com.geeksforless.money.transfer.api.dto.AccountResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.ACCOUNT_RESPONSE;
import static com.geeksforless.money.transfer.api.fixture.AccountTestData.createDefaultAccount;
import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    private final AccountMapper target = Mappers.getMapper(AccountMapper.class);

    @Test
    void shouldConvertToResponse() {

        AccountEntity account = createDefaultAccount();

        AccountResponse accountResponse = target.convertToResponse(account);

        assertThat(accountResponse).isEqualTo(ACCOUNT_RESPONSE);

    }
}
