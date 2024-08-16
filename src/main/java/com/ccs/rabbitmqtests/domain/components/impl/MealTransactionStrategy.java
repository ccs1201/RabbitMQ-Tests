package com.ccs.rabbitmqtests.domain.components.impl;


import com.ccs.rabbitmqtests.domain.components.TransactionStrategy;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppInsufficientBalanceException;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class MealTransactionStrategy implements TransactionStrategy {

    private final CashTransactionStrategy fallback;
    private static final Set<String> MCC_MEAL = Set.of("5811", "5812");

    @Override
    public TransactionCodesEnum processTransaction(Transaction transaction) {
        try {
            validarSaldo(transaction.getAccount().getBalanceMeal(), transaction.getAmount());
        } catch (AppInsufficientBalanceException e) {
            return fallback.processTransaction(transaction);
        }

        transaction.setTransactionBalanceType(TransactionBalanceTypeEnum.MEAL);
        transaction.getAccount()
                .setBalanceMeal(transaction.getAccount()
                        .getBalanceMeal()
                        .subtract(transaction.getAmount()));

        return TransactionCodesEnum.APROVADA;
    }

    @Override
    public Set<String> getMccs() {
        return MCC_MEAL;
    }

    @Override
    public Optional<TransactionStrategy> getFallback() {
        return Optional.of(fallback);
    }
}