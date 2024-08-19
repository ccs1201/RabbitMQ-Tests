package com.ccs.rabbitmqtests.infra.rabbitmq.consumers;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface RabbitMQConsumer {
    void consumeMessage(@Payload String message, @Headers Map<String, Object> headers);
}
