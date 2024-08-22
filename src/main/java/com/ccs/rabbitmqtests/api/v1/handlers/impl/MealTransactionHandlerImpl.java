package com.ccs.rabbitmqtests.api.v1.handlers.impl;

import com.ccs.rabbitmqtests.api.v1.handlers.annotations.HandlerImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.MealTransactionInput;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.framework.Handler;
import lombok.extern.slf4j.Slf4j;

@HandlerImpl(MealTransactionInput.class)
@Slf4j
public class MealTransactionHandlerImpl implements Handler {

    @Override
    public <T> void handle(T input) {
//            Thread.sleep(5000);
        log.info("Received Message: {}", input);
        throw new AppRuntimeException("Lançando exception proposital para testes no handler");
    }
}
