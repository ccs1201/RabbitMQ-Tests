package com.ccs.rabbitmqtests.api.v1.controllers;

import com.ccs.rabbitmqtests.api.v1.inputs.CashTransactionInput;
import com.ccs.rabbitmqtests.api.v1.inputs.FoodTransactionInput;
import com.ccs.rabbitmqtests.api.v1.inputs.MealTransactionInput;
import com.ccs.rabbitmqtests.api.v1.inputs.TransactionRequest;
import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.impl.RabbitMQPublisherImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final RabbitMQPublisherImpl rabbitMQPublisherImpl;

    @PostMapping
    public void test(@RequestBody TransactionRequest transactionRequest) {

        rabbitMQPublisherImpl.sendAsync(RabbitMQConstants.PAYMENT_SERVICE_QUEUE,
                FoodTransactionInput.tofoodTransactionInput(transactionRequest));

        rabbitMQPublisherImpl.sendAsync(RabbitMQConstants.PAYMENT_SERVICE_QUEUE,
                CashTransactionInput.toCashTransactionInput(transactionRequest));

        rabbitMQPublisherImpl.sendAsync(RabbitMQConstants.PAYMENT_SERVICE_QUEUE,
                MealTransactionInput.toMealTransactionInput(transactionRequest));

        rabbitMQPublisherImpl.sendAsync(RabbitMQConstants.PAYMENT_SERVICE_QUEUE, transactionRequest);
    }
}
