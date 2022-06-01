package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.TransferConverter;
import com.example.finalprojectaozcann.currency.api.CurrencyApi;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.base.BaseBankAccount;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.TransferHistory;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DebitCardRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.repository.TransferHistoryRepository;
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

    //TODO transferDate format ayarlanacak
    @Override
    public SuccessAccountTransferResponse transferCheckingToDeposit(TransferToAccountRequest request,
                                                                    HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount senderAccount = getCheckingAccountByIban(request.senderIban());
        checkLoggerEqualSender(loggedUserId, senderAccount.getUser().getId());
        DepositAccount receiverAccount = getDepositAccountByIban(request.receiverIban());

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

        return transferConverter.toSuccessCardTransferResponse(amount, receiverCard, senderAccount, currencyRate,
                transferHistory.getTransferDate());

    }

    private DebitCard getDebitCardByCardNumber(String cardNumber) {
        return debitCardRepository.findByCardNumberAndIsDeleted(cardNumber, false)
                .orElseThrow(() -> new BusinessServiceOperationException.DebitCardNotFoundException("Debit card not found"));
    }

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
                    .UserCanNotTransferException("Deposit account can only be transferred to the same account owned by the user");
        }
    }

    private CheckingAccount getCheckingAccountByIban(String iban) {
        return checkingAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(iban, false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException("Checking account not found"));
    }


    private DepositAccount getDepositAccountByIban(String iban) {
        return depositAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(iban, false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException("Deposit account not found."));

    }

    private void checkLoggerEqualSender(Long loggerId, Long senderId) {
        if (!(loggerId.equals(senderId))) {
            throw new BusinessServiceOperationException
                    .UserCanNotTransferException("User only can be sent transfer with own account");
        }
    }

    private void compareAmountToSenderAccountBalance(BigDecimal amount, BigDecimal accountBalance) {
        if (amount.compareTo(accountBalance) > 0) {
            throw new BusinessServiceOperationException
                    .AmountCanNotBiggerThanBalanceException("Amount can not be bigger than sender account balance");
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
