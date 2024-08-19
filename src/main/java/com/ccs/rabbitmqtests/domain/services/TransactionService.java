package com.ccs.rabbitmqtests.domain.services;

import com.ccs.rabbitmqtests.api.v1.inputs.TransactionInput;
import jakarta.transaction.Transactional;

public interface TransactionService {
    @Transactional
    String processarTransacao(TransactionInput input);
}
