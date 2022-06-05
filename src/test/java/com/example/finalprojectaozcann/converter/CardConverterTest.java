package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.*;
import com.example.finalprojectaozcann.model.enums.*;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CardConverterTest {

    @InjectMocks
    CardConverter cardConverter;

    private static final String cardNumber = "10_000_000_000_000_00";
    private static final String ccv = "111";

    @Test
    void should_returnBankCard_when_toCreateBankCardMethod() {

        User user = createUser();
        CheckingAccount checkingAccount = createCheckingAccount();

        BankCard expectedBankCard = createBankCard(user);
        BankCard actualBankCard = cardConverter.toCreateBankCard(user, checkingAccount);

        actualBankCard.setCardNumber(cardNumber);
        actualBankCard.setCcv(ccv);

        assertThat(expectedBankCard).isEqualTo(actualBankCard);
    }

    @Test
    void should_returnDebitCard_when_toCreateDebitCardMethod() {

        User user = createUser();
        CheckingAccount checkingAccount = createCheckingAccount();


        DebitCard expectedDebitCard = createDebitCard(user);

        DebitCard actualDebitCard = cardConverter.toCreateDebitCard(user, checkingAccount);

        actualDebitCard.setCardNumber(cardNumber);
        actualDebitCard.setCcv(ccv);

        assertThat(expectedDebitCard).isEqualTo(actualDebitCard);
    }

    @Test
    void should_returnGetBankCardResponse_when_toBankCardResponseMethod() {

        User user = createUser();
        BankCard bankCard = createBankCard(user);
        BigDecimal balance = BigDecimal.ZERO;

        GetBankCardResponse expectedGetBankCardResponse = new GetBankCardResponse(bankCard.getName(),
                bankCard.getSurname(),
                bankCard.getCardType(),
                bankCard.getCardNumber(),
                expiryDateFormatter(bankCard.getExpiryDate()),
                bankCard.getCcv(),
                balance,
                bankCard.getPassword());

        GetBankCardResponse actualGetBankCardResponse = cardConverter.toBankCardResponse(bankCard, balance);

        assertThat(expectedGetBankCardResponse).isEqualTo(actualGetBankCardResponse);
    }

    @Test
    void should_returnGetDebitCardDeptInquiryResponse_when_toGetDebitCardDeptInquiryResponseMethod() {

        DebitCard debitCard = createDebitCard(createUser());

        GetDebitCardDeptInquiryResponse expectedGetDebitCardDeptInquiryResponse =
                new GetDebitCardDeptInquiryResponse(debitCard.getCardNumber(),
                        debitCard.getCardLimit(),
                        debitCard.getDept(),
                        debitCard.getExpendableAmount());

        GetDebitCardDeptInquiryResponse actualGetDebitCardDeptInquiryResponse = cardConverter.toGetDebitCardDeptInquiryResponse(debitCard);

        assertThat(expectedGetDebitCardDeptInquiryResponse).isEqualTo(actualGetDebitCardDeptInquiryResponse);

    }

    @Test
    void should_returnGetDebitCardResponse_when_toDebitCardResponseMethod() {

        User user = createUser();
        DebitCard debitCard = createDebitCard(user);

        GetDebitCardResponse expectedGetDebitCardResponse = new GetDebitCardResponse(debitCard.getName(),
                debitCard.getSurname(),
                debitCard.getCardType(),
                debitCard.getCardNumber(),
                expiryDateFormatter(debitCard.getExpiryDate()),
                debitCard.getCcv(),
                debitCard.getCardLimit(),
                debitCard.getPassword());

        GetDebitCardResponse actualGetDebitCardResponse = cardConverter.toDebitCardResponse(debitCard);

        assertThat(expectedGetDebitCardResponse).isEqualTo(actualGetDebitCardResponse);

    }


    public User createUser() {

        User user = new User();

        user.setName("Ahmet");
        user.setSurname("Ozcan");
        user.setIdentityNumber(12345678911L);
        user.setBirthday(LocalDate.now());
        user.setEmail("admin@admin.com");
        user.setAddress("Istanbul");
        user.setPhoneNumber("05419322436");
        user.setStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.INDIVIDUAL);
        user.setId(1L);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("a1b2c3"));

        Role role = new Role();
        role.setName(RoleType.USER.toString());
        user.setRoles(new HashSet<>(List.of(role)));

        return user;
    }

    public CheckingAccount createCheckingAccount() {

        String accountNumber = "32344479398928916";
        String iban = "TR330006132344479398928916";
        User user = createUser();

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccount.setIban(iban);
        checkingAccount.setCurrency(Currency.TRY);
        checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
        checkingAccount.setUser(user);
        checkingAccount.setCreatedBy(user.getId().toString());
        checkingAccount.setCreatedAt(new Date());

        return checkingAccount;
    }

    public BankCard createBankCard(User user) {

        CheckingAccount checkingAccount = createCheckingAccount();

        BankCard bankCard = new BankCard();
        bankCard.setName(user.getName());
        bankCard.setSurname(user.getSurname());
        bankCard.setCardType(CardType.BANK_CARD);
        bankCard.setCardStatus(CardStatus.ACTIVE);
        bankCard.setCardNumber(cardNumber);
        bankCard.setExpiryDate(LocalDate.now().plusYears(5));
        bankCard.setCcv(ccv);
        bankCard.setUser(user);
        bankCard.setCheckingAccount(checkingAccount);
        bankCard.setPassword("8911");

        return bankCard;
    }

    public String expiryDateFormatter(LocalDate expiryDate) {
        String year = String.valueOf(expiryDate.getYear()).substring(2, 4);
        String month = String.valueOf(expiryDate.getMonth().getValue());

        return month + "/" + year;
    }

    public DebitCard createDebitCard(User user) {

        CheckingAccount checkingAccount = createCheckingAccount();

        DebitCard debitCard = new DebitCard();
        debitCard.setName(user.getName());
        debitCard.setSurname(user.getSurname());
        debitCard.setCardType(CardType.DEBIT_CARD);
        debitCard.setCardStatus(CardStatus.ACTIVE);
        debitCard.setCardNumber(cardNumber);
        debitCard.setExpiryDate(LocalDate.now().plusYears(5));
        debitCard.setCcv(ccv);
        debitCard.setUser(user);
        debitCard.setCardLimit(BigDecimal.valueOf(5_000));
        debitCard.setCheckingAccount(checkingAccount);
        debitCard.setPassword("8911");

        return debitCard;
    }

}
