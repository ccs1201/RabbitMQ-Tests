package com.ccs.rabbitmqtests.domain.services.impl;

import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import com.ccs.rabbitmqtests.domain.repositories.MerchantRepository;
import com.ccs.rabbitmqtests.domain.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Optional<Merchant> findByName(String name) {
        return merchantRepository
                .findByName(name);
    }

    @Override
    public Merchant createMerchant(String name, String mcc) {
        return merchantRepository.save(Merchant.builder()
                .name(name)
                .mcc(mcc)
                .transactions(Collections.emptyList())
                .build());
    }
}
