package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class CheckingAccount extends BaseBankAccount {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Card eklenecek
}
