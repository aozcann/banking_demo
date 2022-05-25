package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseExtendedEntity;
import com.example.finalprojectaozcann.model.enums.AccountType;
import com.example.finalprojectaozcann.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class BankAccount extends BaseExtendedEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal lockedBalance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.TRY;

    @ManyToOne
    private Customer customer;

    private String bankCode;

    private String branchCode;


}
