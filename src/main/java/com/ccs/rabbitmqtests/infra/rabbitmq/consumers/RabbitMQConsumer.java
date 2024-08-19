package com.ccs.rabbitmqtests.infra.rabbitmq.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface RabbitMQConsumer {
    @RabbitListener(queues = "${app.rabbitmq.queue.test}")
    void consumeMessage(@Payload String message, @Headers Map<String, Object> headers);
}
