package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.base.BaseCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.TransferHistory;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessShoppingResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TransferConverter {
    public TransferHistory createTransferHistoryForAccountToAccount(BaseBankAccount senderAccount, BaseBankAccount receiverAccount,
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
        transferHistory.setScheduled(false);

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
                currencyRate,
                null);
    }

    public TransferHistory createTransferHistoryForAccountToCard(BigDecimal amount, DebitCard receiverCard,
                                                                 CheckingAccount senderAccount, String description, BigDecimal currencyRate) {

        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSenderAccountType(senderAccount.getAccountType().toString());
        transferHistory.setSenderId(senderAccount.getId());
        transferHistory.setSenderIban(senderAccount.getIban());
        transferHistory.setSenderCurrency(senderAccount.getCurrency().toString());
        transferHistory.setReceiverId(receiverCard.getId());
        transferHistory.setReceiverCardNumber(receiverCard.getCardNumber());
        transferHistory.setReceiverType(receiverCard.getCardType().toString());
        transferHistory.setTransferDate(LocalDate.now());
        transferHistory.setTransferAmount(amount);
        transferHistory.setDescription(description);
        transferHistory.setCurrencyRate(currencyRate);

        return transferHistory;

    }

    public TransferHistory createTransferHistoryForCardToAccount(BigDecimal amount, BaseBankAccount payeeAccount,
                                                                 BaseCard senderCard, BigDecimal currencyRate) {

        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSenderAccountType(senderCard.getCardType().toString());
        transferHistory.setSenderId(senderCard.getId());
        transferHistory.setSenderCardNumber(senderCard.getCardNumber());
        transferHistory.setSenderCurrency(Currency.TRY.toString());
        transferHistory.setReceiverId(payeeAccount.getId());
        transferHistory.setReceiverIban(payeeAccount.getIban());
        transferHistory.setReceiverType(payeeAccount.getAccountType().toString());
        transferHistory.setTransferDate(LocalDate.now());
        transferHistory.setTransferAmount(amount);
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

    public TransferHistory createATMTransferToCard(Long loggedUserId, BaseCard card, BigDecimal amount) {
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSenderAccountType("ATM");
        transferHistory.setSenderId(loggedUserId);
        transferHistory.setReceiverCardNumber(card.getCardNumber());
        transferHistory.setReceiverId(card.getId());
        transferHistory.setReceiverType(card.getCardType().toString());
        transferHistory.setTransferDate(LocalDate.now());
        transferHistory.setTransferAmount(amount);

        return transferHistory;

    }

    public SuccessShoppingResponse toSuccessShoppingResponse(BaseCard card,
                                                             CheckingAccount checkingAccount, BigDecimal amount, TransferHistory transferHistory) {

        return new SuccessShoppingResponse(card.getCardNumber(),
                card.getName(),
                checkingAccount.getUser().getName(),
                amount.toString(),
                transferHistory.getTransferDate().toString());
    }

    public SuccessAccountTransferResponse toSuccessAccountTransferResponse(String message) {
        return new SuccessAccountTransferResponse(null,
                null,
                null,
                null,
                null,
                null,
                message);
    }
}
