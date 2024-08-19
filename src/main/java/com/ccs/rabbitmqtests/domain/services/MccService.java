package com.ccs.rabbitmqtests.domain.services;

import com.ccs.rabbitmqtests.domain.models.entities.Mcc;

public interface MccService {

    Mcc findByMcc(String mcc);
}
