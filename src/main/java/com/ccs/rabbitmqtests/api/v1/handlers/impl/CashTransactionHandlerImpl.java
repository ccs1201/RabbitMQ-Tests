package com.ccs.rabbitmqtests.api.v1.handlers.impl;

import com.ccs.rabbitmqtests.api.v1.handlers.annotations.HandlerImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.CashTransactionInput;
import com.ccs.rabbitmqtests.framework.Handler;
import lombok.extern.slf4j.Slf4j;

@HandlerImpl(CashTransactionInput.class)
@Slf4j
public class CashTransactionHandlerImpl implements Handler {

    @Override
    public <T> void handle(T input) {
        log.info("Received message: " + input);
    }
}
