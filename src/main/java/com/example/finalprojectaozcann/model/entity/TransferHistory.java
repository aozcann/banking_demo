package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
public class TransferHistory extends BaseEntity {

    private String senderAccountType;

    private String senderCardNumber;

    private Long senderId;

    private String senderIban;

    private String senderCurrency;

    private Long receiverId;

    private String receiverIban;

    private String receiverCurrency;

    private String receiverCardNumber;

    private String receiverType;

    private BigDecimal transferAmount;

    private LocalDate transferDate;

    private String description;

    private BigDecimal currencyRate;

    private boolean isScheduled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransferHistory that = (TransferHistory) o;
        return isScheduled == that.isScheduled && Objects.equals(senderAccountType, that.senderAccountType) && Objects.equals(senderCardNumber, that.senderCardNumber) && Objects.equals(senderId, that.senderId) && Objects.equals(senderIban, that.senderIban) && Objects.equals(senderCurrency, that.senderCurrency) && Objects.equals(receiverId, that.receiverId) && Objects.equals(receiverIban, that.receiverIban) && Objects.equals(receiverCurrency, that.receiverCurrency) && Objects.equals(receiverCardNumber, that.receiverCardNumber) && Objects.equals(receiverType, that.receiverType) && Objects.equals(transferAmount, that.transferAmount) && Objects.equals(transferDate, that.transferDate) && Objects.equals(description, that.description) && Objects.equals(currencyRate, that.currencyRate);
    }

}
