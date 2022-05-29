package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.enums.Maturity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class DepositAccount extends BaseBankAccount {
    @Enumerated(EnumType.STRING)
    private Maturity maturity;

    private BigDecimal interestRate;

    private BigDecimal balanceWithInterest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
