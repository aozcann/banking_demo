package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.entity.*;
import com.example.finalprojectaozcann.model.enums.*;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessShoppingResponse;
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
class TransferConverterTest {


    @InjectMocks
    TransferConverter transferConverter;

    public static final BigDecimal amount = BigDecimal.ONE;
    public static final BigDecimal currencyRate = BigDecimal.ONE;
    public static final LocalDate transferDate = LocalDate.now();
    public static final String description = "test";


    @Test
    void should_returnTransferHistory_when_createTransferHistoryForAccountToAccountMethod() {
        User user = createUser();

        CheckingAccount senderAccount = createCheckingAccount(user);
        DepositAccount receiverAccount = createDepositAccount(user);

        TransferHistory expectedTransferHistory = createTransferHistoryForAccount(senderAccount, receiverAccount);

        TransferHistory actualTransferHistory = transferConverter
                .createTransferHistoryForAccountToAccount(senderAccount, receiverAccount, amount, currencyRate, description);

        assertThat(expectedTransferHistory).isEqualTo(actualTransferHistory);
    }

    @Test
    void should_returnSuccessAccountTransferResponse_when_toSuccessAccountTransferResponseMethod() {

        User user = createUser();

        CheckingAccount senderAccount = createCheckingAccount(user);
        DepositAccount receiverAccount = createDepositAccount(user);

        SuccessAccountTransferResponse expectedSuccessAccountTransferResponse = new SuccessAccountTransferResponse(receiverAccount.getIban(),
                amount,
                transferDate,
                receiverAccount.getUser().getName(),
                senderAccount.getUser().getName(),
                currencyRate,
                null);

        SuccessAccountTransferResponse actualSuccessAccountTransferResponse = transferConverter
                .toSuccessAccountTransferResponse(amount, receiverAccount, senderAccount, currencyRate, transferDate);

        assertThat(expectedSuccessAccountTransferResponse).isEqualTo(actualSuccessAccountTransferResponse);
    }

    @Test
    void should_returnTransferHistory_when_createTransferHistoryForAccountToCardMethod() {
        User user = createUser();

        CheckingAccount senderAccount = createCheckingAccount(user);
        DebitCard debitCard = createDebitCard(user);

        TransferHistory expectedTransferHistory = new TransferHistory();
        expectedTransferHistory.setSenderAccountType(senderAccount.getAccountType().toString());
        expectedTransferHistory.setSenderId(senderAccount.getId());
        expectedTransferHistory.setSenderIban(senderAccount.getIban());
        expectedTransferHistory.setSenderCurrency(senderAccount.getCurrency().toString());
        expectedTransferHistory.setReceiverId(debitCard.getId());
        expectedTransferHistory.setReceiverCardNumber(debitCard.getCardNumber());
        expectedTransferHistory.setReceiverType(debitCard.getCardType().toString());
        expectedTransferHistory.setTransferDate(LocalDate.now());
        expectedTransferHistory.setTransferAmount(amount);
        expectedTransferHistory.setDescription(description);
        expectedTransferHistory.setCurrencyRate(currencyRate);

        TransferHistory actualTransferHistory = transferConverter
                .createTransferHistoryForAccountToCard(amount, debitCard, senderAccount, description, currencyRate);

        assertThat(expectedTransferHistory).isEqualTo(actualTransferHistory);

    }

    @Test
    void should_returnTransferHistory_when_createTransferHistoryForCardToAccountMethod() {
        User user = createUser();
        BankCard bankCard = createBankCard(user);
        CheckingAccount checkingAccount = createCheckingAccount(user);


        TransferHistory expectedTransferHistory = new TransferHistory();
        expectedTransferHistory.setSenderAccountType(bankCard.getCardType().toString());
        expectedTransferHistory.setSenderId(bankCard.getId());
        expectedTransferHistory.setSenderCardNumber(bankCard.getCardNumber());
        expectedTransferHistory.setSenderCurrency(Currency.TRY.toString());
        expectedTransferHistory.setReceiverId(checkingAccount.getId());
        expectedTransferHistory.setReceiverIban(checkingAccount.getIban());
        expectedTransferHistory.setReceiverType(checkingAccount.getAccountType().toString());
        expectedTransferHistory.setTransferDate(LocalDate.now());
        expectedTransferHistory.setTransferAmount(amount);
        expectedTransferHistory.setCurrencyRate(currencyRate);

        TransferHistory actualTransferHistory = transferConverter
                .createTransferHistoryForCardToAccount(amount, checkingAccount, bankCard, currencyRate);

        assertThat(expectedTransferHistory).isEqualTo(actualTransferHistory);

    }

    @Test
    void should_returnSuccessCardTransferResponse_when_toSuccessCardTransferResponseMethod() {
        User user = createUser();

        DebitCard debitCard = createDebitCard(user);
        CheckingAccount checkingAccount = createCheckingAccount(user);


        SuccessCardTransferResponse expectedSuccessCardTransferResponse = new SuccessCardTransferResponse(debitCard.getCardNumber(),
                amount,
                transferDate,
                debitCard.getName(),
                checkingAccount.getUser().getName(),
                currencyRate);

        SuccessCardTransferResponse actualSuccessCardTransferResponse = transferConverter
                .toSuccessCardTransferResponse(amount, debitCard, checkingAccount, currencyRate, transferDate);

        assertThat(expectedSuccessCardTransferResponse).isEqualTo(actualSuccessCardTransferResponse);

    }

    @Test
    void should_returnSuccessATMTransferResponse_when_toSuccessATMTransferResponseMethod() {
        User user = createUser();

        DebitCard debitCard = createDebitCard(user);
        SuccessATMTransferResponse expectedSuccessATMTransferResponse = new SuccessATMTransferResponse(debitCard.getCardNumber(), amount, transferDate);

        SuccessATMTransferResponse actualSuccessATMTransferResponse = transferConverter.toSuccessATMTransferResponse(amount, debitCard);

        assertThat(expectedSuccessATMTransferResponse).isEqualTo(actualSuccessATMTransferResponse);

    }


    @Test
    void should_returnTransferHistory_when_createATMTransferToCardMethod() {

        User user = createUser();
        BankCard bankCard = createBankCard(user);

        TransferHistory expectedTransferHistory = new TransferHistory();
        expectedTransferHistory.setSenderAccountType("ATM");
        expectedTransferHistory.setSenderId(user.getId());
        expectedTransferHistory.setReceiverCardNumber(bankCard.getCardNumber());
        expectedTransferHistory.setReceiverId(bankCard.getId());
        expectedTransferHistory.setReceiverType(bankCard.getCardType().toString());
        expectedTransferHistory.setTransferDate(LocalDate.now());
        expectedTransferHistory.setTransferAmount(amount);

        TransferHistory actualTransferHistory = transferConverter
                .createATMTransferToCard(user.getId(), bankCard, amount);

        assertThat(expectedTransferHistory).isEqualTo(actualTransferHistory);
    }

    @Test
    void should_returnSuccessShoppingResponse_when_toSuccessShoppingResponseMethod() {
        User user = createUser();
        BankCard bankCard = createBankCard(user);
        CheckingAccount checkingAccount = createCheckingAccount(user);

        TransferHistory transferHistory = createTransferHistoryForAccount(checkingAccount, checkingAccount);

        SuccessShoppingResponse expectedSuccessShoppingResponse = new SuccessShoppingResponse(bankCard.getCardNumber(),
                bankCard.getName(),
                checkingAccount.getUser().getName(),
                amount.toString(),
                transferDate.toString());

        SuccessShoppingResponse actualSuccessShoppingResponse = transferConverter
                .toSuccessShoppingResponse(bankCard, checkingAccount, amount, transferHistory);

        assertThat(expectedSuccessShoppingResponse).isEqualTo(actualSuccessShoppingResponse);

    }


    @Test
    void should_returnSuccessAccountTransferResponseMessage_when_toSuccessAccountTransferResponseMethod() {

        SuccessAccountTransferResponse expectedSuccessAccountTransferResponse = new SuccessAccountTransferResponse(null,
                null,
                null,
                null,
                null,
                null,
                description);

        SuccessAccountTransferResponse actualSuccessAccountTransferResponse = transferConverter.toSuccessAccountTransferResponse(description);

        assertThat(expectedSuccessAccountTransferResponse).isEqualTo(actualSuccessAccountTransferResponse);

    }


    public CheckingAccount createCheckingAccount(User user) {

        String accountNumber = "32344479398928916";
        String iban = "TR330006132344479398928916";

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

    public DepositAccount createDepositAccount(User user) {
        String accountNumber = "32344479398928916";
        String iban = "TR330006132344479398928916";

        CreateDepositAccountRequest request = new CreateDepositAccountRequest(Currency.TRY, Maturity.DAILY);

        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountType(AccountType.DEPOSIT);
        depositAccount.setAccountNumber(accountNumber);
        depositAccount.setIban(iban);
        depositAccount.setCurrency(Currency.TRY);
        depositAccount.setAccountStatus(AccountStatus.ACTIVE);
        depositAccount.setUser(user);
        depositAccount.setCreatedBy(user.getId().toString());
        depositAccount.setCreatedAt(new Date());
        depositAccount.setMaturity(Maturity.DAILY);
        depositAccount.setBalance(BigDecimal.ZERO);
        depositAccount.setInterestRate(null);

        return depositAccount;
    }

    public TransferHistory createTransferHistoryForAccount(BaseBankAccount senderAccount,
                                                           BaseBankAccount receiverAccount) {
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


    public BankCard createBankCard(User user) {

        String cardNumber = "10_000_000_000_000_00";
        String ccv = "111";

        CheckingAccount checkingAccount = createCheckingAccount(user);

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

    public DebitCard createDebitCard(User user) {

        String cardNumber = "10_000_000_000_000_00";
        String ccv = "111";
        CheckingAccount checkingAccount = createCheckingAccount(user);

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
