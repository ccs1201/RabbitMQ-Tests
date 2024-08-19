package com.ccs.rabbitmqtests.domain.models.entities;

import com.ccs.rabbitmqtests.domain.models.enums.TransactionBalanceTypeEnum;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, length = 4, nullable = false)
    private String code;
    @Enumerated(EnumType.STRING)
    private TransactionBalanceTypeEnum balanceType;
    @OneToMany(mappedBy = "mcc", fetch = FetchType.LAZY)
    private Collection<Merchant> merchants;
}
