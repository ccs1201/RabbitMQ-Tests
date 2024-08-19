package com.ccs.rabbitmqtests.domain.components.impl;


import com.ccs.rabbitmqtests.domain.components.TransactionExecutor;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppInsufficientBalanceException;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
@Qualifier("foodTransactionExecutor")
public class FoodTransactionExecutor implements TransactionExecutor {

    private final CashTransactionExecutor fallback;
    private static final Set<String> MCC_FOOD = Set.of("5411", "5412");

    @Override
    public TransactionCodesEnum processTransaction(Transaction transaction) {
        try {
            validarSaldo(transaction.getAccount().getBalanceFood(), transaction.getAmount());
        } catch (AppInsufficientBalanceException e) {
            return fallback.processTransaction(transaction);
        }

        transaction.setTransactionBalanceType(TransactionBalanceTypeEnum.FOOD);
        transaction.getAccount()
                .setBalanceFood(transaction.getAccount()
                        .getBalanceFood()
                        .subtract(transaction.getAmount()));

        return TransactionCodesEnum.APROVADA;
    }

    @Override
    public Set<String> getMccs() {
        return MCC_FOOD;
    }

    @Override
    public Optional<TransactionExecutor> getFallback() {
        return Optional.of(fallback);
    }
}