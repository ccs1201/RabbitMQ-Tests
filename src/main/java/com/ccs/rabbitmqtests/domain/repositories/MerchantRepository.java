package com.ccs.rabbitmqtests.domain.repositories;


import com.ccs.rabbitmqtests.domain.models.entities.Merchant;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @EntityGraph(attributePaths = {"mcc"})
    Optional<Merchant> findByName(String name);
}