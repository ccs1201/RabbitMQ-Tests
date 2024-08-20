package com.ccs.rabbitmqtests.domain.services.impl;


import com.ccs.rabbitmqtests.api.v1.inputs.TransactionInput;
import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import com.ccs.rabbitmqtests.domain.services.AccountService;
import com.ccs.rabbitmqtests.domain.services.MerchantService;
import com.ccs.rabbitmqtests.domain.services.TransactionService;
import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.RabbitMQPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;
    private final MerchantService merchantService;
    private final RabbitMQPublisher rabbitMQPublisher;

    @Override
    public String processarTransacao(TransactionInput input) {
        accountService.existsById(input.account());
        var merchant = getOrCreateMerchant(input);

        var payload = TransactionPayload.toTransactionPayload(input, merchant);

        if (TransactionBalanceTypeEnum.FOOD.equals(merchant.getMcc().getBalanceType())) {
            return rabbitMQPublisher
                    .sendAndReceiveResponse(RabbitMQConstants.ROUTING_KEY_FOOD, payload, TransactionCodesEnum.class)
                    .getValue();
        }

        if (TransactionBalanceTypeEnum.MEAL.equals(merchant.getMcc().getBalanceType())) {
            return rabbitMQPublisher
                    .sendAndReceiveResponse(RabbitMQConstants.ROUTING_KEY_MEAL, payload, TransactionCodesEnum.class)
                    .getValue();
        }
        
        return rabbitMQPublisher
                .sendAndReceiveResponse(RabbitMQConstants.ROUTING_KEY_CASH, payload, TransactionCodesEnum.class)
                .getValue();
    }

    private Merchant getOrCreateMerchant(TransactionInput transaction) {
        return merchantService.findByNameFetchMcc(transaction.merchant())
                .orElseGet(() -> merchantService
                        .createMerchant(transaction.merchant(), transaction.mcc()));
    }
}
