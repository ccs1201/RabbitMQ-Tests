package com.ccs.rabbitmqtests.api.v1.controllers;

import com.ccs.rabbitmqtests.api.v1.inputs.TransactionInput;
import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.impl.RabbitMQPublisherImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestRabbitMQController {

    private final RabbitMQPublisherImpl rabbitMQPublisherImpl;

    @PostMapping
    public void test(@RequestBody TransactionInput transactionInput) {
        rabbitMQPublisherImpl.sendMessage(RabbitMQConstants.ROUTING_KEY_TEST, transactionInput);
    }
}
