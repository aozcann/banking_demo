package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.base.BaseCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.TransferHistory;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TransferConverter {
    public TransferHistory createTransferHistoryForAccount(BaseBankAccount senderAccount, BaseBankAccount receiverAccount,
                                                           BigDecimal amount, BigDecimal currencyRate, String description) {

        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setReceiverType(senderAccount.getAccountType().toString());
        transferHistory.setSenderId(senderAccount.getId());
        transferHistory.setSenderIban(senderAccount.getIban());
        transferHistory.setSenderCurrency(senderAccount.getCurrency().toString());
        transferHistory.setReceiverId(receiverAccount.getId());
        transferHistory.setReceiverIban(receiverAccount.getIban());
        transferHistory.setReceiverCurrency(receiverAccount.getCurrency().toString());
        transferHistory.setReceiverType(receiverAccount.getAccountType().toString());
        transferHistory.setTransferDate(LocalDate.now());
        transferHistory.setTransferAmount(amount);
        transferHistory.setDescription(description);
        transferHistory.setCurrencyRate(currencyRate);

        return transferHistory;
    }

    public SuccessAccountTransferResponse toSuccessAccountTransferResponse(BigDecimal amount, BaseBankAccount receiverAccount,
                                                                           BaseBankAccount senderAccount, BigDecimal currencyRate,
                                                                           LocalDate transferDate) {

        return new SuccessAccountTransferResponse(receiverAccount.getIban(),
                amount,
                transferDate,
                receiverAccount.getUser().getName(),
                senderAccount.getUser().getName(),
                currencyRate);
    }

    public TransferHistory createTransferHistoryForCard(BigDecimal amount, DebitCard receiverCard,
                                                        CheckingAccount senderAccount, String description, BigDecimal currencyRate) {

        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSenderAccountType(senderAccount.getAccountType().toString());
        transferHistory.setSenderId(senderAccount.getId());
        transferHistory.setSenderIban(senderAccount.getIban());
        transferHistory.setSenderCurrency(senderAccount.getCurrency().toString());
        transferHistory.setReceiverId(receiverCard.getId());
        transferHistory.setReceiverDebitCardNumber(receiverCard.getCardNumber());
        transferHistory.setReceiverType(receiverCard.getCardType().toString());
        transferHistory.setTransferDate(LocalDate.now());
        transferHistory.setTransferAmount(amount);
        transferHistory.setDescription(description);
        transferHistory.setCurrencyRate(currencyRate);

        return transferHistory;
    }

    public SuccessCardTransferResponse toSuccessCardTransferResponse(BigDecimal amount, DebitCard receiverCard, CheckingAccount senderAccount, BigDecimal currencyRate, LocalDate transferDate) {
        return new SuccessCardTransferResponse(receiverCard.getCardNumber(),
                amount,
                transferDate,
                receiverCard.getName(),
                senderAccount.getUser().getName(),
                currencyRate);
    }

    public SuccessATMTransferResponse toSuccessATMTransferResponse(BigDecimal amount, BaseCard card) {
        return new SuccessATMTransferResponse(card.getCardNumber(), amount, LocalDate.now());
    }

    public TransferHistory createATMTransferToCard(Long loggedUserıd, BaseCard card, BigDecimal amount) {
        TransferHistory transferHistory = new TransferHistory();
    transferHistory.setSenderAccountType("ATM");
    transferHistory.setSenderId(loggedUserıd);
    transferHistory.setReceiverDebitCardNumber(card.getCardNumber());
    transferHistory.setReceiverId(card.getId());
    transferHistory.setReceiverType(card.getCardType().toString());
    transferHistory.setTransferDate(LocalDate.now());
    transferHistory.setTransferAmount(amount);

    return transferHistory;

    }
}
