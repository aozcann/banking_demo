package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Setter
@Getter
@Entity
public class BankCard extends BaseCard {

    @OneToOne
    private CheckingAccount checkingAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(checkingAccount, bankCard.checkingAccount);
    }

}