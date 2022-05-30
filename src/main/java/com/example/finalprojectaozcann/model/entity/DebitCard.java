
package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class DebitCard extends BaseCard {

    @Column(nullable = false)
    private BigDecimal cardLimit;

    @ManyToOne
    @JoinColumn(name = "checkingAccount_id")
    private CheckingAccount checkingAccount;

}
