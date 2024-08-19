package com.ccs.rabbitmqtests.infra.rabbitmq.publishers;

public interface RabbitMQPublisher {
    void sendMessage(Object message);

    void sendMessage(String exchange, String routingKey, Object message);

    void sendMessage(String routingKey, Object message);
}
