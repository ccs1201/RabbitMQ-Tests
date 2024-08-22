package com.ccs.rabbitmqtests.api.v1.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FoodTransactionInput(@NotNull Long account,
                                   @NotNull @Positive BigDecimal totalAmount,
                                   @NotBlank String mcc,
                                   @NotBlank String merchant) {

    public static FoodTransactionInput tofoodTransactionInput(TransactionRequest transactionRequest) {
        return new FoodTransactionInput(transactionRequest.account(),
                transactionRequest.totalAmount(),
                transactionRequest.mcc(),
                transactionRequest.merchant());
    }
}
