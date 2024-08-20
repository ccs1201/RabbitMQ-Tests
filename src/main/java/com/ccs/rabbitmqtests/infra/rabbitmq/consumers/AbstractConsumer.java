package com.ccs.rabbitmqtests.infra.rabbitmq.consumers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractConsumer {

    protected final void log(Object payload) {
        log.info("Consumer: {}, Payload: {}", this.getClass().getSimpleName(), payload);
    }
}
