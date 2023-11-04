package com.geeksforless.money.transfer.api.functional.tests;

import com.geeksforless.money.transfer.api.functional.tests.steps.TransactionSteps;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseFunctionalTest {

    @Autowired
    private ExecutorService testExecutorService;

    @Autowired
    protected TransactionSteps transactionSteps;

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        transactionSteps.setPort(port);
    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public ExecutorService testExecutorService() {
            return Executors.newFixedThreadPool(10);
        }

    }

    protected <T> T[] run10Times(Supplier<T> supplier, IntFunction<T[]> arrayCollector) {
        return IntStream.rangeClosed(1, 10)
                .mapToObj(i -> supplier.get())
                .toArray(arrayCollector);
    }

    protected CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, testExecutorService);
    }
}
