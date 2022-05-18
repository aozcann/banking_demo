package com.example.finalprojectaozcann.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class BankAccount extends BaseExtendedEntity {
    private AccountType accountType;
    private Long accountNumber;


    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;


}
