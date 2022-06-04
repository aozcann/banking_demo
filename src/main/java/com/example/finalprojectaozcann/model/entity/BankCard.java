package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Setter
@Getter
@Entity
public class BankCard extends BaseCard {

    @OneToOne
    private CheckingAccount checkingAccount;

}