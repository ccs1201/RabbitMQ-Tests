package com.ccs.rabbitmqtests.api.v1.handlers.impl;

import com.ccs.rabbitmqtests.api.v1.handlers.annotations.HandlerImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.FoodTransactionInput;
import com.ccs.rabbitmqtests.framework.Handler;
import lombok.extern.slf4j.Slf4j;

@HandlerImpl(FoodTransactionInput.class)
@Slf4j
public class FoodTransactionHandlerImpl implements Handler<FoodTransactionInput> {

    @Override
    public void handle(FoodTransactionInput input) {
        log.info("Received message: " + input);
    }
}
