package com.ccs.rabbitmqtests.domain.services;

import com.ccs.rabbitmqtests.api.v1.inputs.TransactionRequest;
import jakarta.transaction.Transactional;

public interface TransactionService {
    @Transactional
    String processarTransacao(TransactionRequest input);
}
