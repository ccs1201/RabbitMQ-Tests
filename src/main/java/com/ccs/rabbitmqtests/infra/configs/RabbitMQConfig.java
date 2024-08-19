package com.ccs.rabbitmqtests.infra.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    @Bean
    public Queue queue(@Value("${app.rabbitmq.queue.test}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange,
                           @Value("${app.rabbitmq.routing.key.test}") String routingKey) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    @Bean
    public Queue foodQueue() {
        return new Queue(RabbitMQConstants.QUEUE_FOOD, true);
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
        return new Queue(RabbitMQConstants.QUEUE_MEAL, true);
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
        return new Queue(RabbitMQConstants.QUEUE_CASH, true);
    }

    @Bean
    public Binding cashBinding(Queue cashQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(cashQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_CASH);
    }

    @Bean
    public MessageProperties messageProperties() {
        var messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
}

