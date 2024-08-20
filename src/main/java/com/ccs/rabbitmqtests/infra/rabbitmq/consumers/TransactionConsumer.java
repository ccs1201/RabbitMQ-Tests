package com.ccs.rabbitmqtests.infra.rabbitmq.consumers;

import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;

public interface TransactionConsumer {

    TransactionCodesEnum consumeCashTransaction(TransactionPayload payload);
}
