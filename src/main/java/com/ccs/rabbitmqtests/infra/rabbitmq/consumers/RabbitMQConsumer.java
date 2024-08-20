package com.ccs.rabbitmqtests.infra.rabbitmq.consumers;

import org.springframework.amqp.core.Message;

public interface RabbitMQConsumer {
    void consumeMessage(Message message);
}
