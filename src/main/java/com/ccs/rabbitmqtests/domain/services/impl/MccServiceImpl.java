package com.ccs.rabbitmqtests.domain.services.impl;

import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.domain.models.entities.Mcc;
import com.ccs.rabbitmqtests.domain.repositories.MccRepository;
import com.ccs.rabbitmqtests.domain.services.MccService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MccServiceImpl implements MccService {

    private final MccRepository mccRepository;

    public Mcc findByMcc(String mccCode) {
        return mccRepository.findByCode(mccCode)
                .orElseThrow(() -> new AppRuntimeException("MCC not found"));
    }

    @Override
    public Mcc findById(Long id) {
        return mccRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException("MCC not found"));
    }


}
