package com.ccs.rabbitmqtests.infra.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue(@Value("${app.rabbitmq.queue.test}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange topicExchange(@Value("${app.rabbitmq.exchange.test}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange,
                           @Value("${app.rabbitmq.routing.key.test}") String routingKey) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey);
    }
}

