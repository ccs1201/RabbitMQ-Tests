package com.ccs.rabbitmqtests.infra.rabbitmq.consumers.impl;

import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.infra.rabbitmq.consumers.RabbitMQConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerImpl implements RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {RabbitMQConstants.QUEUE_TEST})
    @Override
    public void consumeMessage(@Payload Message message) {
        CompletableFuture.runAsync(() -> processMessage(message), Executors.newVirtualThreadPerTaskExecutor());
    }

    private void processMessage(Message message) {
        //criar dinamicamente o objeto pelo __TypeId__
        var typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        if (Objects.nonNull(typeId)) {
            try {
                Object obj = objectMapper.readValue(message.getBody(), Class.forName(typeId));
                log.info("""
                        
                        Received message
                        Headers: {}
                        body: {}
                        """, message.getMessageProperties().getHeaders(), obj);
                return;
            } catch (ClassNotFoundException e) {
                log.error("Failed to convert message. Invalid type: {}", typeId, e);
                throw new AppRuntimeException(String.format("Failed to convert message. Class not found: %s", typeId), e);
            } catch (IOException e) {
                throw new AppRuntimeException(e);
            }
        }
        log.warn("Missing __TypeId__ header in the message");
    }
}
