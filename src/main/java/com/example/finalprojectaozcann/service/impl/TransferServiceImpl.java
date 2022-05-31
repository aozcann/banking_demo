package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.currency.api.CurrencyApi;
import com.example.finalprojectaozcann.exception.BaseException;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.TransferHistory;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.TransferSuccessResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.repository.TransferHistoryRepository;
import com.example.finalprojectaozcann.service.TransferService;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final CurrencyApi currencyApi;
    private final TransferHistoryRepository transferHistoryRepository;
    private final JWTDecodeUtil jwtDecodeUtil;

    //TODO transferDate format ayarlanacak
    @Override
    public TransferSuccessResponse transferCheckingToDeposit(TransferToAccountRequest request, HttpServletRequest httpServletRequest) {

        Long loggerId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);


        CheckingAccount senderAccount = checkingAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(request.senderIban(), false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException.AccountNotFoundException("Checking account not found"));

        if (!(loggerId.equals(senderAccount.getUser().getId()))){
            throw new BaseException("User only can be sent transfer with own account");
        }

        DepositAccount receiverAccount = depositAccountRepository
                .findByIbanAndIsDeletedAndAccountStatus(request.receiverIban(), false, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException.AccountNotFoundException("Deposit account not found."));

        if (request.amount().compareTo(senderAccount.getBalance()) > 0) {
            //TODO hata özelleştirilecek
            throw new BaseException("Amount can not be bigger than sender Account balance");
        }


        if (senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {

            receiverAccount.setBalance(receiverAccount.getBalance().add(request.amount()));
            senderAccount.setBalance(senderAccount.getBalance().subtract(request.amount()));
            //TODO lockedbalance eklenecek

        } else {
            String senderAccountCurrency = senderAccount.getCurrency().toString();
            String receiverAccountCurrency = receiverAccount.getCurrency().toString();
            BigDecimal newAmount = request.amount().multiply(currencyApi.getCurrencyRate(senderAccountCurrency, receiverAccountCurrency));
            receiverAccount.setBalance(receiverAccount.getBalance().add(newAmount));
            senderAccount.setBalance(senderAccount.getBalance().subtract(newAmount));
        }

        checkingAccountRepository.save(senderAccount);
        depositAccountRepository.save(receiverAccount);


        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSenderId(senderAccount.getId());
        transferHistory.setReceiverId(receiverAccount.getId());
        transferHistory.setTransferDate(LocalDate.now());
        //TODO new amount olabilir
        transferHistory.setTransferAmount(request.amount());
        transferHistory.setDescription(request.description());
        transferHistoryRepository.save(transferHistory);

        return new TransferSuccessResponse(request.receiverIban()
                , request.amount(),
                LocalDate.now(),
                receiverAccount.getUser().getName(),
                senderAccount.getUser().getName());


//        BigDecimal currency = currencyApi.getCurrencyRate(Currency.TRY.toString(), Currency.EUR.toString());

    }


}
