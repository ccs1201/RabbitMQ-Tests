package com.ccs.rabbitmqtests.infra.rabbitmq.consumers.impl;

import com.ccs.rabbitmqtests.domain.core.constants.AppConstants;
import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import com.ccs.rabbitmqtests.domain.services.impl.MealTransactionExecutorServiceImpl;
import com.ccs.rabbitmqtests.infra.rabbitmq.consumers.AbstractConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealTransactionConsumer extends AbstractConsumer {

    private final MealTransactionExecutorServiceImpl service;

    @RabbitListener(queues = AppConstants.RabbitMQConstants.QUEUE_MEAL)
    public TransactionCodesEnum consumeCashTransaction(@Payload TransactionPayload payload) {
        log(payload);
        return service.processarTransacao(payload);
    }
}
