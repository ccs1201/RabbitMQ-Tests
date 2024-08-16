package com.ccs.rabbitmqtests.domain.core.exceptions;


import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import lombok.Getter;

@Getter
public class AppInsufficientBalanceException extends AppRuntimeException {

    private final TransactionCodesEnum code = TransactionCodesEnum.SALDO_INSUFICIENTE;

    public AppInsufficientBalanceException(String tipoSaldo) {
        super("%S Saldo insuficiente".formatted(tipoSaldo));
    }
}
