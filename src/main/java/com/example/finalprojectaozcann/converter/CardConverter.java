package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.CardType;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class CardConverter {
    public BankCard toCreateBankCard(CreateCardRequest request, User user, CheckingAccount checkingAccount) {

        //Todo accountnumber ve her hesabın sadce 1 banka kartı olur.
        BankCard bankCard = new BankCard();
        bankCard.setName(user.getName());
        bankCard.setSurname(user.getSurname());
        bankCard.setCardType(CardType.BANK_CARD);
        bankCard.setCardNumber(createCardNumber());
        bankCard.setExpiryDate(LocalDate.now().plusYears(5));
        bankCard.setCcv(createCCV());
        bankCard.setUser(user);
        bankCard.setBalance(checkingAccount.getBalance());
        bankCard.setCheckingAccount(checkingAccount);
        bankCard.setPassword(createCardPassword(user.getIdentityNumber().toString()));

        return bankCard;
    }

    public GetBankCardResponse toBankCardToBankCardResponse(BankCard bankCard) {

        return new GetBankCardResponse(bankCard.getName(),
                bankCard.getSurname(),
                bankCard.getCardType(),
                bankCard.getCardNumber(),
                bankCard.getExpiryDate(),
                bankCard.getCcv(),
                bankCard.getBalance(),
                bankCard.getPassword());
    }

    public String createCardNumber() {
        long cardNumber = 10_000_000_000_000_00L + new Random().nextLong(89_999_999_999_999_99L);
        return Long.toString(cardNumber);
    }

    public String createCCV() {
        String stringCCV = "";
        for (int i = 0; i < 3; i++) {
            int ccv = new Random().nextInt(9);
            stringCCV = stringCCV + ccv;
        }
        return stringCCV;
    }

    //The last 4 numbers of the ID number from the user is the first password
    public String createCardPassword(String identityNumber) {
        return identityNumber.substring(identityNumber.length() - 4);
    }

}
