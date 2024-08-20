package com.ccs.rabbitmqtests.domain.services.impl;

import com.ccs.rabbitmqtests.domain.components.TransactionExecutor;
import com.ccs.rabbitmqtests.domain.core.payloads.TransactionPayload;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import com.ccs.rabbitmqtests.domain.repositories.TransactionRepository;
import com.ccs.rabbitmqtests.domain.services.AccountService;
import com.ccs.rabbitmqtests.domain.services.MerchantService;
import com.ccs.rabbitmqtests.domain.services.TransactionExecutorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodTransactionExecutorServiceImpl implements TransactionExecutorService {

    private static final TransactionBalanceTypeEnum TRANSACTION_TYPE_FOOD = TransactionBalanceTypeEnum.FOOD;
    private final TransactionRepository transactionRepository;
    private final TransactionExecutor foodTransactionExecutor;
    private final AccountService accountService;
    private final MerchantService merchantService;


    @Override
    @Transactional
    public TransactionCodesEnum processarTransacao(TransactionPayload transactionPayload) {
        var transaction = buildTransaction(transactionPayload,
                accountService.findByIdLocking(transactionPayload.accountId()),
                merchantService.findbyId(transactionPayload.merchantId()),
                TRANSACTION_TYPE_FOOD);

        var transactionCode = foodTransactionExecutor.processTransaction(transaction);

        if (transactionCode == TransactionCodesEnum.APROVADA) {
            transactionRepository.save(transaction);
        }

        return transactionCode;
    }

}
