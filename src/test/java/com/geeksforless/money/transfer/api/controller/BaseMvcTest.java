package com.geeksforless.money.transfer.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;

import static org.mockito.Mockito.when;

public abstract class BaseMvcTest {

    protected static final Instant INSTANT = Instant.now();
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private Clock clock;

    @BeforeEach
    void init() {
        when(clock.instant()).thenReturn(INSTANT);
    }
}
