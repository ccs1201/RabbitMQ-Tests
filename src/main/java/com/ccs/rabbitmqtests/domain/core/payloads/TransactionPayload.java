package com.ccs.rabbitmqtests.domain.core.payloads;

import com.ccs.rabbitmqtests.api.v1.inputs.TransactionRequest;
import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionPayload(@NotNull Long accountId,
                                 @NotNull @Positive BigDecimal totalAmount,
                                 @NotBlank String mcc,
                                 @NotNull Long merchantId) {
    public static TransactionPayload toTransactionPayload(TransactionRequest input, Merchant merchant) {

        return new TransactionPayload(input.account(), input.totalAmount(), input.mcc(), merchant.getId());

    }
}
