package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.converter.TransferConverter;
import com.example.finalprojectaozcann.currency.api.CurrencyApi;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.entity.*;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.ShoppingWithCardRequest;
import com.example.finalprojectaozcann.model.request.TransferATMToCardRequest;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessShoppingResponse;
import com.example.finalprojectaozcann.repository.*;
import com.example.finalprojectaozcann.service.TransferService;
import com.example.finalprojectaozcann.utils.DateUtil;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final CurrencyApi currencyApi;
    private final TransferHistoryRepository transferHistoryRepository;
    private final JWTDecodeUtil jwtDecodeUtil;
    private final TransferConverter transferConverter;
    private final DebitCardRepository debitCardRepository;
    private final BankCardRepository bankCardRepository;

    @Override
    public SuccessAccountTransferResponse transferCheckingToDeposit(TransferToAccountRequest request,
                                                                    HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount senderAccount = getCheckingAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        DepositAccount receiverAccount = getDepositAccountByIban(request.receiverIban());

        if (!(DateUtil.dateFormatStringToLocalDate(request.transferDate()).equals(LocalDate.now()))) {

            TransferHistory transferHistory = transferConverter.createTransferHistoryForAccountToAccount(senderAccount,
                    receiverAccount, request.amount(), BigDecimal.ONE, request.description());
            transferHistory.setScheduled(true);
            transferHistory.setTransferDate(DateUtil.dateFormatStringToLocalDate(request.transferDate()));
            transferHistoryRepository.save(transferHistory);

            return transferConverter.toSuccessAccountTransferResponse(Constants.Message.SCHEDULED_TRANSFER);
        }

        List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsBalance(request.amount(),
                senderAccount, receiverAccount);

        BigDecimal amount = amountAndCurrencyRate.get(0);
        BigDecimal currencyRate = amountAndCurrencyRate.get(1);

        checkingAccountRepository.save(senderAccount);
        depositAccountRepository.save(receiverAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccountToAccount(senderAccount,
                receiverAccount, amount, currencyRate, request.description());
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessAccountTransferResponse(amount, receiverAccount, senderAccount, currencyRate,
                transferHistory.getTransferDate());
    }

    @Scheduled(cron = "0 1 1 ? * *") // every day 1:01 am
    public void scheduledTransfer() {
        Collection<TransferHistory> scheduledTransferList = transferHistoryRepository.findAllByIsScheduled(true);
        for (TransferHistory transferHistory : scheduledTransferList) {
            if (transferHistory.getTransferDate().equals(LocalDate.now())) {

                CheckingAccount senderAccount = getCheckingAccountByIban(transferHistory.getSenderIban());
                DepositAccount receiverAccount = getDepositAccountByIban(transferHistory.getReceiverIban());

                List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsBalance(transferHistory.getTransferAmount(),
                        senderAccount, receiverAccount);

                BigDecimal amount = amountAndCurrencyRate.get(0);
                BigDecimal currencyRate = amountAndCurrencyRate.get(1);

                checkingAccountRepository.save(senderAccount);
                depositAccountRepository.save(receiverAccount);

                transferHistory.setTransferAmount(amount);
                transferHistory.setCurrencyRate(currencyRate);
                transferHistory.setScheduled(false);

                transferHistoryRepository.save(transferHistory);
            }
        }
    }

    @Override
    public SuccessAccountTransferResponse transferDepositToChecking(TransferToAccountRequest request,
                                                                    HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        DepositAccount senderAccount = getDepositAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        CheckingAccount receiverAccount = getCheckingAccountByIban(request.receiverIban());

        compareDepositAccountOwnerToReceiverAccountUser(receiverAccount, senderAccount);

        List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsBalance(request.amount(),
                senderAccount, receiverAccount);

        BigDecimal amount = amountAndCurrencyRate.get(0);
        BigDecimal currencyRate = amountAndCurrencyRate.get(1);

        checkingAccountRepository.save(receiverAccount);
        depositAccountRepository.save(senderAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccountToAccount(receiverAccount,
                senderAccount, amount, currencyRate, request.description());
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessAccountTransferResponse(amount, senderAccount, receiverAccount, currencyRate,
                transferHistory.getTransferDate());

    }

    @Override
    public SuccessAccountTransferResponse transferCheckingToChecking(TransferToAccountRequest request,
                                                                     HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount senderAccount = getCheckingAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        CheckingAccount receiverAccount = getCheckingAccountByIban(request.receiverIban());

        List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsBalance(request.amount(),
                senderAccount, receiverAccount);

        BigDecimal amount = amountAndCurrencyRate.get(0);
        BigDecimal currencyRate = amountAndCurrencyRate.get(1);

        checkingAccountRepository.save(senderAccount);
        checkingAccountRepository.save(receiverAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccountToAccount(senderAccount,
                receiverAccount, amount, currencyRate, request.description());
        transferHistoryRepository.save(transferHistory);


        return transferConverter.toSuccessAccountTransferResponse(amount, receiverAccount, senderAccount, currencyRate,
                transferHistory.getTransferDate());
    }

    @Override
    public SuccessCardTransferResponse transferCheckingToDebitCard(TransferCheckingAccountToDebitCardRequest request,
                                                                   HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount senderAccount = getCheckingAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        DebitCard receiverCard = getDebitCardByCardNumber(request.cardNumber());

        BigDecimal amount = request.amount();

        compareAmountToSenderAccountBalance(amount, senderAccount.getBalance());

        BigDecimal currencyRate = getCurrencyRateForTransfer(senderAccount.getCurrency(), Currency.TRY);
        amount = amount.multiply(currencyRate);

        BigDecimal dept = receiverCard.getDept();
        receiverCard.setDept(dept.subtract(amount));
        receiverCard.setExpendableAmount(receiverCard.getCardLimit().subtract(receiverCard.getDept()));
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));

        checkingAccountRepository.save(senderAccount);
        debitCardRepository.save(receiverCard);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccountToCard(amount, receiverCard,
                senderAccount, request.description(), currencyRate);
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessCardTransferResponse(amount, receiverCard, senderAccount, currencyRate,
                transferHistory.getTransferDate());

    }

    @Override
    public SuccessATMTransferResponse transferATMToDebitCard(TransferATMToCardRequest request, HttpServletRequest httpServletRequest) {
        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        DebitCard debitCard = debitCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));
        String password = request.password();

        if (!(password.equals(debitCard.getPassword()))) {
            throw new BusinessServiceOperationException
                    .CardPasswordIsWrongException(Constants.ErrorMessage.CARD_PASSWORD_IS_WRONG);
        }

        BigDecimal amount = request.amount();

        debitCard.setDept(debitCard.getDept().subtract(amount));
        debitCard.setExpendableAmount(debitCard.getCardLimit().subtract(debitCard.getDept()));
        debitCardRepository.save(debitCard);

        TransferHistory transferHistory = transferConverter.createATMTransferToCard(loggedUserId, debitCard, amount);
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessATMTransferResponse(amount, debitCard);

    }

    @Override
    public SuccessATMTransferResponse transferATMToBankCard(TransferATMToCardRequest request, HttpServletRequest httpServletRequest) {
        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        BankCard bankCard = bankCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .BankCardNotFoundException(Constants.ErrorMessage.BANK_CARD_NOT_FOUND));
        String password = request.password();

        if (!(password.equals(bankCard.getPassword()))) {
            throw new BusinessServiceOperationException
                    .CardPasswordIsWrongException(Constants.ErrorMessage.CARD_PASSWORD_IS_WRONG);
        }

        BigDecimal amount = request.amount();

        CheckingAccount checkingAccount = bankCard.getCheckingAccount();
        checkingAccount.setBalance(amount);
        checkingAccountRepository.save(checkingAccount);

        TransferHistory transferHistory = transferConverter.createATMTransferToCard(loggedUserId, bankCard, amount);
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessATMTransferResponse(amount, bankCard);
    }

    @Override
    public SuccessShoppingResponse shoppingWithDebitCard(ShoppingWithCardRequest request
            , HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        DebitCard debitCard = debitCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));

        checkLoggerEqualSender(loggedUserId, debitCard.getUser().getId());

        if (!(request.ccv().equals(debitCard.getCcv()))) {
            throw new BusinessServiceOperationException.CardCcvIsWrongException(Constants.ErrorMessage.CARD_CCV_IS_WRONG);
        }
        if (!(request.password().equals(debitCard.getPassword()))) {
            throw new BusinessServiceOperationException.CardPasswordIsWrongException(Constants.ErrorMessage.CARD_CCV_IS_WRONG);
        }
        if (!(DateUtil.dateFormatStringToLocalDate(request.expiryDate()).equals(debitCard.getExpiryDate()))) {
            throw new BusinessServiceOperationException.CardExpiryDateIsWrongException(Constants.ErrorMessage.CARD_EXPIRY_DATE_IS_WRONG);
        }

        compareAmountToSenderAccountBalance(request.price(), debitCard.getExpendableAmount());

        CheckingAccount checkingAccount = getCheckingAccountByIban(request.payeeIBAN());
        BigDecimal amount = request.price();

        BigDecimal currencyRate = getCurrencyRateForTransfer(Currency.TRY, checkingAccount.getCurrency());
        amount = amount.multiply(currencyRate);

        debitCard.setDept(debitCard.getDept().add(amount));
        debitCard.setExpendableAmount(debitCard.getCardLimit().subtract(debitCard.getDept()));
        debitCardRepository.save(debitCard);

        checkingAccount.setBalance(checkingAccount.getBalance().add(amount));
        checkingAccountRepository.save(checkingAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForCardToAccount(amount, checkingAccount, debitCard, currencyRate);
        transferHistoryRepository.save(transferHistory);
        return transferConverter.toSuccessShoppingResponse(debitCard, checkingAccount, amount, transferHistory);
    }

    @Override
    public SuccessShoppingResponse shoppingWithBankCard(ShoppingWithCardRequest request, HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        BankCard bankCard = bankCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .BankCardNotFoundException(Constants.ErrorMessage.BANK_CARD_NOT_FOUND));

        checkLoggerEqualSender(loggedUserId, bankCard.getUser().getId());

        if (!(request.ccv().equals(bankCard.getCcv()))) {
            throw new BusinessServiceOperationException.CardCcvIsWrongException(Constants.ErrorMessage.CARD_CCV_IS_WRONG);
        }
        if (!(request.password().equals(bankCard.getPassword()))) {
            throw new BusinessServiceOperationException.CardPasswordIsWrongException(Constants.ErrorMessage.CARD_CCV_IS_WRONG);
        }
        if (!(DateUtil.dateFormatStringToLocalDate(request.expiryDate()).equals(bankCard.getExpiryDate()))) {
            throw new BusinessServiceOperationException.CardExpiryDateIsWrongException(Constants.ErrorMessage.CARD_EXPIRY_DATE_IS_WRONG);
        }

        compareAmountToSenderAccountBalance(request.price(), bankCard.getCheckingAccount().getBalance());

        CheckingAccount checkingAccount = getCheckingAccountByIban(request.payeeIBAN());
        BigDecimal amount = request.price();

        BigDecimal currencyRate = getCurrencyRateForTransfer(Currency.TRY, checkingAccount.getCurrency());
        amount = amount.multiply(currencyRate);

        bankCard.getCheckingAccount().setBalance(bankCard.getCheckingAccount().getBalance().subtract(amount));
        bankCardRepository.save(bankCard);

        checkingAccount.setBalance(checkingAccount.getBalance().add(amount));
        checkingAccountRepository.save(checkingAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForCardToAccount(amount, checkingAccount, bankCard, currencyRate);
        transferHistoryRepository.save(transferHistory);
        return transferConverter.toSuccessShoppingResponse(bankCard, checkingAccount, amount, transferHistory);

    }


    private DebitCard getDebitCardByCardNumber(String cardNumber) {
        return debitCardRepository.findByCardNumberAndIsDeleted(cardNumber, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));
    }


    private List<BigDecimal> setSenderAndReceiverAccountsBalance(BigDecimal amount, BaseBankAccount senderAccount,
                                                                 BaseBankAccount receiverAccount) {

        compareAmountToSenderAccountBalance(amount, senderAccount.getBalance());

        BigDecimal currencyRate = getCurrencyRateForTransfer(senderAccount.getCurrency(), receiverAccount.getCurrency());
        amount = amount.multiply(currencyRate);

        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));

        return new ArrayList<>(Arrays.asList(amount, currencyRate));

    }

    private void compareDepositAccountOwnerToReceiverAccountUser(CheckingAccount receiverAccount,
                                                                 DepositAccount senderAccount) {
        if (!(receiverAccount.getUser().equals(senderAccount.getUser()))) {
            throw new BusinessServiceOperationException
                    .UserCanNotTransferException(Constants.ErrorMessage.DEPOSIT_ACCOUNT_CAN_ONLY_BE_TRANSFERRED_TO_THE_SAME_ACCOUNT_OWNED_BY_THE_USER);
        }
    }

    private CheckingAccount getCheckingAccountByIban(String iban) {
        return checkingAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(iban, false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException(Constants.ErrorMessage.CHECKING_ACCOUNT_NOT_FOUND));
    }

    private DepositAccount getDepositAccountByIban(String iban) {
        return depositAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(iban, false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException(Constants.ErrorMessage.DEPOSIT_ACCOUNT_NOT_FOUND));
    }

    private void checkLoggerEqualSender(Long loggerId, Long senderId) {
        if (!(loggerId.equals(senderId))) {
            throw new BusinessServiceOperationException
                    .UserCanNotTransferException(Constants.ErrorMessage.USER_ONLY_CAN_BE_SENT_TRANSFER_WITH_OWN_ACCOUNT);
        }
    }

    private void compareAmountToSenderAccountBalance(BigDecimal amount, BigDecimal accountBalance) {
        if (amount.compareTo(accountBalance) > 0) {
            throw new BusinessServiceOperationException
                    .AmountCanNotBiggerThanBalanceException(Constants.ErrorMessage.AMOUNT_CAN_NOT_BE_BIGGER_THAN_SENDER_ACCOUNT_BALANCE);
        }
    }

    private BigDecimal getCurrencyRateForTransfer(Currency senderCurrency, Currency receiverCurrency) {
        if (!(senderCurrency.equals(receiverCurrency))) {
            String senderAccountCurrency = senderCurrency.toString();
            String receiverAccountCurrency = receiverCurrency.toString();
            return currencyApi.getCurrencyRate(senderAccountCurrency, receiverAccountCurrency);
        }
        return BigDecimal.ONE;
    }

}
