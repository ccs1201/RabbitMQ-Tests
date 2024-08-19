package com.ccs.rabbitmqtests.api.v1.controllers;


import com.ccs.rabbitmqtests.api.v1.inputs.TransactionInput;
import com.ccs.rabbitmqtests.api.v1.outputs.TransactionResponse;
import com.ccs.rabbitmqtests.domain.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse process(@Valid @RequestBody TransactionInput input) {
        return new TransactionResponse(transactionService.processarTransacao(input));

    }

    @PostMapping("/async")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<TransactionResponse> processAsync(@Valid @RequestBody TransactionInput input) {
        return CompletableFuture.supplyAsync(() ->
                transactionService.processarTransacao(input), Executors.newVirtualThreadPerTaskExecutor()
        ).thenApply(TransactionResponse::new);

    }
}
