package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.enums.Maturity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Setter
@Getter
public class DepositAccount extends BaseBankAccount {
    @Enumerated(EnumType.STRING)
    private Maturity maturity;

    private BigDecimal interestRate;

    private BigDecimal balanceWithInterest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepositAccount that = (DepositAccount) o;
        return maturity == that.maturity && Objects.equals(interestRate, that.interestRate) && Objects.equals(balanceWithInterest, that.balanceWithInterest);
    }

}
