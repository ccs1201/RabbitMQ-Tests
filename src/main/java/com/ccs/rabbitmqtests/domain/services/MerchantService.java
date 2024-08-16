package com.ccs.rabbitmqtests.domain.services;


import com.ccs.rabbitmqtests.domain.models.entities.Merchant;

import java.util.Optional;

public interface MerchantService {
    Optional<Merchant> findByName(String name);

    Merchant createMerchant(String name, String mcc);
}
