package com.ccs.rabbitmqtests.domain.services.impl;


import com.ccs.rabbitmqtests.api.v1.inputs.TransactionInput;
import com.ccs.rabbitmqtests.domain.components.TransactionStrategyFactory;
import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import com.ccs.rabbitmqtests.domain.models.enums.TransactionCodesEnum;
import com.ccs.rabbitmqtests.domain.repositories.TransactionRepository;
import com.ccs.rabbitmqtests.domain.services.AccountService;
import com.ccs.rabbitmqtests.domain.services.MerchantService;
import com.ccs.rabbitmqtests.domain.services.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionStrategyFactory transactionStrategyFactory;
    private final MerchantService merchantService;

    @Transactional
    @Override
    public String executeTransaction(TransactionInput input) {
        var account = accountService.findByIdLocking(input.account());
        var merchant = getOrCreateMerchant(input);
        var transaction = Transaction.builder()
                .account(account)
                .amount(input.totalAmount())
                .merchant(merchant)
                .mcc(merchant.getMcc())
                .build();

        var strategy = transactionStrategyFactory.getStrategy(transaction.getMcc());

        var transactionCode = strategy.processTransaction(transaction);

        if (transactionCode == TransactionCodesEnum.APROVADA) {
            transactionRepository.save(transaction);
        }

        return transactionCode.getValue();
    }

    private Merchant getOrCreateMerchant(TransactionInput transaction) {
        return merchantService.findByName(transaction.merchant())
                .orElseGet(() -> merchantService
                        .createMerchant(transaction.merchant(), transaction.mcc()));
    }
}
