package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.CardStatus;
import com.example.finalprojectaozcann.model.enums.CardType;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Component
public class CardConverter {
    public BankCard toCreateBankCard(User user, CheckingAccount checkingAccount) {

        BankCard bankCard = new BankCard();
        bankCard.setName(user.getName());
        bankCard.setSurname(user.getSurname());
        bankCard.setCardType(CardType.BANK_CARD);
        bankCard.setCardStatus(CardStatus.ACTIVE);
        bankCard.setCardNumber(createCardNumber());
        bankCard.setExpiryDate(LocalDate.now().plusYears(5));
        bankCard.setCcv(createCCV());
        bankCard.setUser(user);
        bankCard.setCheckingAccount(checkingAccount);
        bankCard.setPassword(createCardPassword(user.getIdentityNumber().toString()));

        return bankCard;
    }

    public DebitCard toCreateDebitCard(User user, CheckingAccount checkingAccount) {

        DebitCard debitCard = new DebitCard();
        debitCard.setName(user.getName());
        debitCard.setSurname(user.getSurname());
        debitCard.setCardType(CardType.DEBIT_CARD);
        debitCard.setCardStatus(CardStatus.ACTIVE);
        debitCard.setCardNumber(createCardNumber());
        debitCard.setExpiryDate(LocalDate.now().plusYears(5));
        debitCard.setCcv(createCCV());
        debitCard.setUser(user);
        debitCard.setCardLimit(BigDecimal.valueOf(5_000));
        debitCard.setCheckingAccount(checkingAccount);
        debitCard.setPassword(createCardPassword(user.getIdentityNumber().toString()));

        return debitCard;
    }

    public GetBankCardResponse toBankCardResponse(BankCard bankCard, BigDecimal balance) {

        return new GetBankCardResponse(bankCard.getName(),
                bankCard.getSurname(),
                bankCard.getCardType(),
                bankCard.getCardNumber(),
                expiryDateFormatter(bankCard.getExpiryDate()),
                bankCard.getCcv(),
                balance,
                bankCard.getPassword());
    }

    public GetDebitCardResponse toDebitCardResponse(DebitCard debitCard) {

        return new GetDebitCardResponse(debitCard.getName(),
                debitCard.getSurname(),
                debitCard.getCardType(),
                debitCard.getCardNumber(),
                expiryDateFormatter(debitCard.getExpiryDate()),
                debitCard.getCcv(),
                debitCard.getCardLimit(),
                debitCard.getPassword());
    }

    public GetDebitCardDeptInquiryResponse toGetDebitCardDeptInquiryResponse(DebitCard debitCard) {

        return new GetDebitCardDeptInquiryResponse(debitCard.getCardNumber(),
                debitCard.getCardLimit(),
                debitCard.getDept(),
                debitCard.getExpendableAmount());
    }

    public String createCardNumber() {
        long cardNumber = 10_000_000_000_000_00L + new Random().nextLong(89_999_999_999_999_99L);
        return Long.toString(cardNumber);
    }

    public String createCCV() {
        StringBuilder stringBuilderCCV = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int ccv = new Random().nextInt(9);
            stringBuilderCCV.append(ccv);
        }
        return stringBuilderCCV.toString();
    }

    //The last 4 numbers of the ID number from the user is the first password
    public String createCardPassword(String identityNumber) {
        return identityNumber.substring(identityNumber.length() - 4);
    }

    public String expiryDateFormatter(LocalDate expiryDate) {
        String year = String.valueOf(expiryDate.getYear()).substring(2, 4);
        String month = String.valueOf(expiryDate.getMonth().getValue());

        return month + "/" + year;
    }

}
