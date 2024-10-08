package com.ccs.rabbitmqtests.domain.repositories;


import com.ccs.rabbitmqtests.domain.models.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}