package com.ccs.rabbitmqtests.infra.rabbitmq.publishers.impl;

import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.RabbitMQPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQPublisherImpl implements RabbitMQPublisher {


    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String routingKey, Object message) {
        sendMessage(RabbitMQConstants.EXCHANGE_NAME, routingKey, message);
    }

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        log(exchange, routingKey, message);

        rabbitTemplate.convertAndSend(exchange, routingKey, message, messageToSend -> {
                    messageToSend.getMessageProperties().setHeader("timestamp", OffsetDateTime.now());
                    messageToSend.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                    return messageToSend;
                }
        );
    }

    public <T> T sendAndReceiveResponse(String routingKey, Object message, Class<T> responseClass) {
        log(RabbitMQConstants.EXCHANGE_NAME, routingKey, message);
        return (T) rabbitTemplate.convertSendAndReceive(RabbitMQConstants.EXCHANGE_NAME, routingKey, message);
    }

    private static void log(String exchange, String routingKey, Object message) {
        log.info("""
                
                Sending message to RabbitMQ
                Exchange: {}
                RoutigKey: {}
                message: {}
                """, exchange, routingKey, message);
    }
}
