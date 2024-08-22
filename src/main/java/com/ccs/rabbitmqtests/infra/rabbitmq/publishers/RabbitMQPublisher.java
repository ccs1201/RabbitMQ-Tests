package com.ccs.rabbitmqtests.infra.rabbitmq.publishers;

public interface RabbitMQPublisher {
    void sendAsync(String exchange, String routingKey, Object message);

    void sendAsync(String routingKey, Object message);

    <T> T call(String routingKey, Object message, Class<T> responseClass);
}
