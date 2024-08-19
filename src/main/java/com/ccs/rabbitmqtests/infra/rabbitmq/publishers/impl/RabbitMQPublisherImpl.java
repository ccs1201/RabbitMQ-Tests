package com.ccs.rabbitmqtests.infra.rabbitmq.publishers.impl;

import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.RabbitMQPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQPublisherImpl implements RabbitMQPublisher {

    @Value("${app.rabbitmq.exchange.test}")
    private String exchange;

    @Value("${app.rabbitmq.routing.key.test}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void inti() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
    }

    @Override
    public void sendMessage(Object message) {
        sendMessage(exchange, routingKey, message);
    }

    @Override
    public void sendMessage(String routingKey, Object message) {
        sendMessage(exchange, routingKey, message);
    }

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        log.info("""
                
                Sending message to RabbitMQ
                Exchange: {}
                RoutigKey: {}
                message: {}
                """, exchange, routingKey, message);

        rabbitTemplate.convertAndSend(exchange, routingKey, message, messageToSend -> {
                    messageToSend.getMessageProperties().setHeader("timestamp", OffsetDateTime.now());
                    messageToSend.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                    return messageToSend;
                }
        );
    }
}
