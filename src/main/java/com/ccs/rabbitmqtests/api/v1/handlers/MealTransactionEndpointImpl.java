package com.ccs.rabbitmqtests.api.v1.handlers;

import com.ccs.rabbitmqtests.framework.annotations.EndpointImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.MealTransactionInput;
import com.ccs.rabbitmqtests.framework.Endpoint;
import lombok.extern.slf4j.Slf4j;

@EndpointImpl(forClass = MealTransactionInput.class)
@Slf4j
public class MealTransactionEndpointImpl implements Endpoint<MealTransactionInput, Void> {

    @Override
    public Void handle(MealTransactionInput input) {
//            Thread.sleep(5000);
        log.info("Received Message: {}", input);
        //throw new AppRuntimeException("Lançando exception proposital para testes no handler");
        return null;
    }
}
