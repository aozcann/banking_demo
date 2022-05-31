package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseCard;
import com.example.finalprojectaozcann.model.base.BaseExtendedEntity;
import com.example.finalprojectaozcann.model.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class BankCard extends BaseCard {
//
//    @Column(nullable = false)
//    private BigDecimal balance;

    @OneToOne
    private CheckingAccount checkingAccount;

}