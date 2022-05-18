package com.example.finalprojectaozcann.entity;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
public class Customer extends BaseExtendedEntity {

    private String name;
    private String lastname;
    private Long identity;
    private Date birthday;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<BankAccount> bankAccount;
}
