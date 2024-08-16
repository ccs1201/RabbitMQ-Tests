package com.ccs.rabbitmqtests.infra.rabbitmq.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@Slf4j
public class RabbitMQPublisher {

    @Value("${app.rabbitmq.exchange.test}")
    private String exchange;

    @Value("${app.rabbitmq.routing.key.test}")
    private String routingKey;

    private final MessageProperties messageProperties;

    public RabbitMQPublisher(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
        this.messageProperties = new MessageProperties();
        this.messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
    }

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message) {
        log.info("""
                
                Sending message to RabbitMQ
                Exchange: {}
                RoutigKey: {}
                message: {}
                """, exchange, routingKey, message);

        messageProperties.setHeaders(getHeader());
        rabbitTemplate.send(exchange, routingKey, rabbitTemplate.getMessageConverter()
                .toMessage(message, messageProperties));
    }

    private Map<String, Object> getHeader() {
        return Map.of("timestamp", OffsetDateTime.now().toString());
    }
}
