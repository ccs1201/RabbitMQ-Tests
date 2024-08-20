package com.ccs.rabbitmqtests.domain.services;


import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface MerchantService {
    Optional<Merchant> findByNameFetchMcc(String name);

    Merchant createMerchant(String name, String mcc);

    Merchant findbyId(@NotNull Long aLong);
}
