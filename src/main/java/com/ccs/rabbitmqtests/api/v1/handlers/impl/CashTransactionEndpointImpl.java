package com.ccs.rabbitmqtests.api.v1.handlers.impl;

import com.ccs.rabbitmqtests.framework.annotations.EndpointImpl;
import com.ccs.rabbitmqtests.api.v1.inputs.CashTransactionInput;
import com.ccs.rabbitmqtests.framework.Endpoint;
import lombok.extern.slf4j.Slf4j;

@EndpointImpl(forClass = CashTransactionInput.class)
@Slf4j
public class CashTransactionEndpointImpl implements Endpoint<CashTransactionInput, Void> {

    @Override
    public Void handle(CashTransactionInput input) {
        log.info("Received message: " + input);
        return null;
    }
}
