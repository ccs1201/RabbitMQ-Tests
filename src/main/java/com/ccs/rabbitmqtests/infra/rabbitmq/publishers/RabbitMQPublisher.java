package com.ccs.rabbitmqtests.infra.rabbitmq.publishers;

public interface RabbitMQPublisher {
    void sendMessage(String exchange, String routingKey, Object message);

    void sendMessage(String routingKey, Object message);

    <T> T sendAndReceiveResponse (String routingKey, Object message, Class<T> responseClass);
}
