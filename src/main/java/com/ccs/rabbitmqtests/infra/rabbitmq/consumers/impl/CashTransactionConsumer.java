package com.ccs.rabbitmqtests.infra.rabbitmq.consumers.impl;

import com.ccs.rabbitmqtests.domain.core.constants.AppConstants;
import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import com.ccs.rabbitmqtests.domain.services.impl.CashTransactionExecutorServiceImpl;
import com.ccs.rabbitmqtests.infra.rabbitmq.consumers.AbstractConsumer;
import com.ccs.rabbitmqtests.infra.rabbitmq.consumers.TransactionConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashTransactionConsumer extends AbstractConsumer implements TransactionConsumer {

    private final CashTransactionExecutorServiceImpl service;

    @RabbitListener(queues = AppConstants.RabbitMQConstants.QUEUE_CASH)
    public TransactionCodesEnum process(@Payload TransactionPayload payload) {
        log(payload);
        return service.processarTransacao(payload);
    }
}
