package com.ccs.rabbitmqtests.domain.repositories;


import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @EntityGraph(attributePaths = {"mcc"})
    Optional<Merchant> findByName(String name);
}