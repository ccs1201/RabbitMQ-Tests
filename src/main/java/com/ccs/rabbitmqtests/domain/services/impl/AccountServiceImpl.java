package com.ccs.rabbitmqtests.domain.services.impl;


import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.domain.models.entities.Account;
import com.ccs.rabbitmqtests.domain.repositories.AccountRepository;
import com.ccs.rabbitmqtests.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account findByIdLocking(Long id) {
        return accountRepository.findByIdLocking(id).orElseThrow(() ->
                new AppRuntimeException("Account not found"));
    }

    @Override
    public void existsById(Long account) {
        if (!accountRepository.existsById(account)) {
            throw new AppRuntimeException("Account not found");
        }
    }
}
