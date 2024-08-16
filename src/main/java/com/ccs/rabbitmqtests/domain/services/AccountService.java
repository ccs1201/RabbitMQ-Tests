package com.ccs.rabbitmqtests.domain.services;


import com.ccs.rabbitmqtests.domain.models.entities.Account;

public interface AccountService {
    Account findByIdLocking(Long id);

    Account findById(Long id);

    Account save(Account account);
}
