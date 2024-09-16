package com.ccs.rabbitmqtests.api.v1.controllers;


import com.ccs.rabbitmqtests.framework.annotations.EndpointImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.TransactionRequest;
import com.ccs.rabbitmqtests.api.v1.outputs.TransactionResponse;
import com.ccs.rabbitmqtests.domain.services.TransactionService;
import com.ccs.rabbitmqtests.framework.Endpoint;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@EndpointImpl(forClass = TransactionRequest.class)
public class TransactionController implements Endpoint<TransactionRequest, TransactionResponse> {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse handle(@Valid @RequestBody TransactionRequest input) {
        log.info("Recebido no controller {} payload {}", this.getClass().getSimpleName(), input);
        return new TransactionResponse(transactionService.processarTransacao(input));

    }

    @PostMapping("/async")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<TransactionResponse> processAsync(@Valid @RequestBody TransactionRequest input) {
        return CompletableFuture.supplyAsync(() ->
                transactionService.processarTransacao(input), Executors.newVirtualThreadPerTaskExecutor()
        ).thenApply(TransactionResponse::new);

    }

}
