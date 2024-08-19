package com.ccs.rabbitmqtests.domain.repositories;

import com.ccs.rabbitmqtests.domain.models.entities.Mcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MccRepository extends JpaRepository<Mcc, Long> {
  Optional<Mcc> findByCode(String code);
}