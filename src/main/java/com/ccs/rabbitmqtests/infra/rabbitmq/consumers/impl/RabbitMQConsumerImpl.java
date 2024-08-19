package com.ccs.rabbitmqtests.infra.rabbitmq.consumers.impl;

import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.infra.rabbitmq.consumers.RabbitMQConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
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
    public void consumeMessage(@Payload String message, @Headers Map<String, Object> headers) {
        CompletableFuture.runAsync(() -> processMessage(message, headers), Executors.newVirtualThreadPerTaskExecutor());
    }

    private void processMessage(String body, Map<String, Object> headers) {
        log.info("""
                
                Received message
                Headers: {}
                body: {}
                """, headers, body);

        //criar dinamicamente o objeto pelo __TypeId__
        var typeId = headers.get("__TypeId__").toString();
        if (Objects.nonNull(typeId)) {
            try {
                Object obj = objectMapper.readValue(body, Class.forName(typeId));
                log.info("""
                        
                        Objeto convertido: {}
                        """, obj);
                return;
            } catch (ClassNotFoundException e) {
                log.error("Failed to convert message. Invalid type: {}", typeId, e);
                throw new AppRuntimeException(String.format("Failed to convert message. Class not found: %s", typeId), e);
            } catch (JsonProcessingException e) {
                throw new AppRuntimeException(e);
            }
        }
        log.warn("Missing __TypeId__ header in the message");
    }
}
