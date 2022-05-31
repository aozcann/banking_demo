package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;

@Entity
@Getter
@Setter
public class TransferHistory extends BaseEntity {

    private Long senderId;

    private Long receiverId;

    private BigDecimal transferAmount;

    private LocalDate transferDate;

    private String description;

    //TODO currency type eklenecek
//    private Collection<Currency> currencies = new ArrayList<>();

}
