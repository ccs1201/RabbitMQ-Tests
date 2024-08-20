package com.ccs.rabbitmqtests.domain.services.impl;

import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import com.ccs.rabbitmqtests.domain.repositories.MerchantRepository;
import com.ccs.rabbitmqtests.domain.services.MccService;
import com.ccs.rabbitmqtests.domain.services.MerchantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MccService mccService;

    @Override
    public Optional<Merchant> findByNameFetchMcc(String name) {
        return merchantRepository
                .findByName(name);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Merchant createMerchant(String name, String mcc) {
        return merchantRepository.save(Merchant.builder()
                .name(name)
                .mcc(mccService.findByMcc(mcc))
                .transactions(Collections.emptyList())
                .build());
    }

    @Override
    public Merchant findbyId(Long aLong) {
        return merchantRepository.findById(aLong).orElseThrow(() -> new AppRuntimeException("Merchant not found"));
    }
}
