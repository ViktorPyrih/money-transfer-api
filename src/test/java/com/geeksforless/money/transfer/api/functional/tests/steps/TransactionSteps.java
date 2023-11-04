package com.geeksforless.money.transfer.api.functional.tests.steps;

import com.geeksforless.money.transfer.api.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.geeksforless.money.transfer.api.fixture.TransactionTestData.createRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component
@RequiredArgsConstructor
public class TransactionSteps {

    private static final String URL = "http://localhost:%d/api/v1/transactions";

    private final TestRestTemplate rest;

    @Setter
    private int port;

    public void create(long sourceAccountId, long destinationAccountId) {
        ResponseEntity<TransactionResponse> response = rest.postForEntity(URL.formatted(port), createRequest(sourceAccountId, destinationAccountId), TransactionResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }
}
