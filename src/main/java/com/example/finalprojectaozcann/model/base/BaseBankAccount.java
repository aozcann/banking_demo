package com.example.finalprojectaozcann.model.base;

import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.AccountType;
import com.example.finalprojectaozcann.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseBankAccount extends BaseExtendedEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal lockedBalance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBankAccount that = (BaseBankAccount) o;
        return Objects.equals(user, that.user) && accountType == that.accountType && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(iban, that.iban) && Objects.equals(balance, that.balance) && Objects.equals(lockedBalance, that.lockedBalance) && currency == that.currency && accountStatus == that.accountStatus;
    }

}
