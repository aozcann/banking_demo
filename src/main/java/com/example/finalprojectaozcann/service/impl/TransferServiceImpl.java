package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.converter.TransferConverter;
import com.example.finalprojectaozcann.currency.api.CurrencyApi;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.entity.*;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.TransferATMToCardRequest;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.repository.*;
import com.example.finalprojectaozcann.service.TransferService;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

    //TODO transferDate format ayarlanacak , date
    @Override
    public SuccessAccountTransferResponse transferCheckingToDeposit(TransferToAccountRequest request,
                                                                    HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount senderAccount = getCheckingAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        DepositAccount receiverAccount = getDepositAccountByIban(request.receiverIban());

        //TODO job yapılacak
//        if (!(DateUtil.dateFormatStringToLocalDate(request.transferDate()).equals(LocalDate.now()))) {
//
//            List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsLockedBalance(request.amount(),
//                    senderAccount, receiverAccount);
//        }

        List<BigDecimal> amountAndCurrencyRate = setSenderAndReceiverAccountsBalance(request.amount(),
                senderAccount, receiverAccount);

        BigDecimal amount = amountAndCurrencyRate.get(0);
        BigDecimal currencyRate = amountAndCurrencyRate.get(1);

        checkingAccountRepository.save(senderAccount);
        depositAccountRepository.save(receiverAccount);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccount(senderAccount,
                receiverAccount, amount, currencyRate, request.description());
        transferHistoryRepository.save(transferHistory);

        return transferConverter.toSuccessAccountTransferResponse(amount, receiverAccount, senderAccount, currencyRate,
                transferHistory.getTransferDate());
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

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccount(receiverAccount,
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

        TransferHistory transferHistory = transferConverter.createTransferHistoryForAccount(senderAccount,
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
        receiverCard.setExpendableAmount(receiverCard.getCardLimit().subtract(dept));
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));

        checkingAccountRepository.save(senderAccount);
        debitCardRepository.save(receiverCard);

        TransferHistory transferHistory = transferConverter.createTransferHistoryForCard(amount, receiverCard,
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

    private DebitCard getDebitCardByCardNumber(String cardNumber) {
        return debitCardRepository.findByCardNumberAndIsDeleted(cardNumber, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));
    }


//    private List<BigDecimal> setSenderAndReceiverAccountsLockedBalance(BigDecimal amount, CheckingAccount senderAccount,
//                                                                       DepositAccount receiverAccount) {
//
//    }

    private List<BigDecimal> setSenderAndReceiverAccountsBalance(BigDecimal amount, BaseBankAccount senderAccount,
                                                                 BaseBankAccount receiverAccount) {

        compareAmountToSenderAccountBalance(amount, senderAccount.getBalance());

        //TODO lockedbalance eklenecek

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
