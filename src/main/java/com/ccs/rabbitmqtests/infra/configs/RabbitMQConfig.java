package com.ccs.rabbitmqtests.infra.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder
                .topicExchange(RabbitMQConstants.EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageProperties messageProperties() {
        var messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }

    @Bean
    public Queue testQueue() {
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_TEST)
                .autoDelete()
                .build();

    }

    @Bean
    public Binding testBinding(Queue testQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(testQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_TEST);
    }

    @Bean
    public Queue foodQueue() {
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_FOOD)
                .autoDelete()
                .build();
    }

    @Bean
    public Binding foodBinding(Queue foodQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(foodQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_FOOD);
    }

    @Bean
    public Queue mealQueue() {
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_MEAL)
                .autoDelete()
                .build();
    }

    @Bean
    public Binding mealBinding(Queue mealQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(mealQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_MEAL);
    }

    @Bean
    public Queue cashQueue() {
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_CASH)
                .autoDelete()
                .build();
    }

    @Bean
    public Binding cashBinding(Queue cashQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(cashQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_CASH);
    }
}

