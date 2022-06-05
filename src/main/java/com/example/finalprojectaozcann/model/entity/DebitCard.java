package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
public class DebitCard extends BaseCard {

    @Column(nullable = false)
    private BigDecimal cardLimit;

    @ManyToOne
    @JoinColumn(name = "checkingAccount_id")
    private CheckingAccount checkingAccount;

    private BigDecimal dept = BigDecimal.ZERO;

    private BigDecimal expendableAmount = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DebitCard debitCard = (DebitCard) o;
        return Objects.equals(cardLimit, debitCard.cardLimit) && Objects.equals(checkingAccount, debitCard.checkingAccount) && Objects.equals(dept, debitCard.dept) && Objects.equals(expendableAmount, debitCard.expendableAmount);
    }

}
