package com.ccs.rabbitmqtests.domain.services;


import com.ccs.rabbitmqtests.domain.models.entities.Account;
import jakarta.validation.constraints.NotNull;

public interface AccountService {
    Account findByIdLocking(Long id);

    void existsById(@NotNull Long account);
}
