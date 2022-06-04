package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class CheckingAccount extends BaseBankAccount {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BankCard bankCard;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "checkingAccount")
    private Set<DebitCard> debitCards = new HashSet<>();
}
