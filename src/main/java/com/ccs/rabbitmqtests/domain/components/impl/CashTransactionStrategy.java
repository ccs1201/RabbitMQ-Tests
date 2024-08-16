package com.ccs.rabbitmqtests.domain.components.impl;


import com.ccs.rabbitmqtests.domain.components.TransactionStrategy;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppInsufficientBalanceException;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class CashTransactionStrategy implements TransactionStrategy {

    private static final String CASH_TRANSACTION = "cash";
    private static final Set<String> MCC_CASH = Set.of(CASH_TRANSACTION);

    @Override
    public TransactionCodesEnum processTransaction(Transaction transaction) {
        try {
            validarSaldo(transaction.getAccount().getBalanceCash(), transaction.getAmount());
        } catch (AppInsufficientBalanceException e) {
            return e.getCode();
        }

        transaction.setTransactionBalanceType(TransactionBalanceTypeEnum.CASH);
        transaction.getAccount()
                .setBalanceCash(transaction.getAccount()
                        .getBalanceCash()
                        .subtract(transaction.getAmount()));

        return TransactionCodesEnum.APROVADA;
    }

    @Override
    public Set<String> getMccs() {
        return MCC_CASH;
    }

    @Override
    public Optional<TransactionStrategy> getFallback() {
        return Optional.empty();
    }
}