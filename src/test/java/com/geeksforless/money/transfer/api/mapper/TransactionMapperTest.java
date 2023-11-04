package com.geeksforless.money.transfer.api.mapper;

import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import com.geeksforless.money.transfer.api.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.geeksforless.money.transfer.api.fixture.AccountTestData.createAccount;
import static com.geeksforless.money.transfer.api.fixture.TransactionTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperTest {

    private final TransactionMapper target = Mappers.getMapper(TransactionMapper.class);

    @Test
    void shouldConvertToResponse() {

        AccountEntity source = createAccount(SOURCE_NAME, SOURCE_SURNAME);
        AccountEntity destination = createAccount(DESTINATION_NAME, DESTINATION_SURNAME);

        TransactionResponse response = target.convertToResponse(source, destination, AMOUNT);

        assertThat(response).isEqualTo(TRANSACTION_RESPONSE);

    }
}
