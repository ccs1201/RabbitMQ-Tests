package com.ccs.rabbitmqtests.domain.components;


import com.ccs.rabbitmqtests.domain.core.exceptions.AppInsufficientBalanceException;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface TransactionStrategy {

    default void validarSaldo(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new AppInsufficientBalanceException(this.getClass().getSimpleName());

    }

    TransactionCodesEnum processTransaction(Transaction transactio);

    Set<String> getMccs();

    Optional<TransactionStrategy> getFallback();
}
