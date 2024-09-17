package com.ccs.rabbitmqtests.api.v1.handlers;

import com.ccs.rabbitmqtests.framework.annotations.EndpointImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.FoodTransactionInput;
import com.ccs.rabbitmqtests.framework.Endpoint;
import lombok.extern.slf4j.Slf4j;

@EndpointImpl(forClass = FoodTransactionInput.class)
@Slf4j
public class FoodTransactionEndpointImpl implements Endpoint<FoodTransactionInput, String> {

    @Override
    public String handle(FoodTransactionInput input) {
        log.info("Received message: {}", input);
        return input.getClass().getSimpleName() + " OK - processado com sucesso";
    }
}
