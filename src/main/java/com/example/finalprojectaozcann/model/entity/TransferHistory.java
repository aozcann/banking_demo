package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TransferHistory extends BaseEntity {

    private String senderAccountType;

    private Long senderId;

    private String senderIban;

    private String senderCurrency;

    private Long receiverId;

    private String receiverIban;

    private String receiverCurrency;

    private String receiverDebitCardNumber;

    //TODO isimlendirme düşünülecek
    private String receiverType;

    private BigDecimal transferAmount;

    private LocalDate transferDate;

    private String description;

    private BigDecimal currencyRate;

}
