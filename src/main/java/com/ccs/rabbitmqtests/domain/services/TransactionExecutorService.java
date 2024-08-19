package com.ccs.rabbitmqtests.domain.services;

import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.entities.Account;
import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;

public interface TransactionExecutorService {

    String processarTransacao(TransactionPayload transactionPayload);

    default Transaction buildTransaction(TransactionPayload transactionPayload, Account account, Merchant merchant, TransactionBalanceTypeEnum balanceTypeEnum) {
        return Transaction.builder()
                .account(account)
                .amount(transactionPayload.totalAmount())
                .merchant(merchant)
                .transactionBalanceType(balanceTypeEnum)
                .build();
    }
}
