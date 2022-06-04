package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.repository.UserRepository;
import com.example.finalprojectaozcann.security.CustomJWTAuthenticationFilter;
import com.example.finalprojectaozcann.service.BankAccountService;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final BankAccountConverter bankAccountConverter;
    private final UserRepository userRepository;
    private final CustomJWTAuthenticationFilter customJWTAuthenticationFilter;
    private final JWTDecodeUtil jwtDecodeUtil;

    @Override
    public GetBankAccountResponse createChecking(CreateCheckingAccountRequest request, HttpServletRequest httpServletRequest) {

        User user = findByIdAndIsDeletedAndStatus(httpServletRequest);
        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(request.currency(), user);
        checkingAccountRepository.save(checkingAccount);
        log.info("Checking account created by id -> {}", checkingAccount.getId());
        return new GetBankAccountResponse(checkingAccount.getId());
    }

    @Override
    public GetBankAccountResponse createDeposit(CreateDepositAccountRequest request, HttpServletRequest httpServletRequest) {

        User user = findByIdAndIsDeletedAndStatus(httpServletRequest);
        DepositAccount depositAccount = bankAccountConverter.toCreateDepositAccount(request, user);
        depositAccountRepository.save(depositAccount);
        log.info("Deposit account created by id -> {}", depositAccount.getId());
        return new GetBankAccountResponse(depositAccount.getId());
    }

    @Override
    public boolean deleteCheckingAccountById(Long id, HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        CheckingAccount checkingAccount = checkingAccountRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException(Constants.ErrorMessage.ACCOUNT_NOT_FOUND));

        if (!(checkingAccount.getUser().getId().equals(loggedUserId))) {
            throw new BusinessServiceOperationException.LoggerCanOnlyDeleteOwnAccountException(Constants.ErrorMessage.LOGGER_CAN_ONLY_DELETE_OWN_ACCOUNT);
        }
        if (checkingAccount.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessServiceOperationException.AccountCanNotDeleteException(Constants.ErrorMessage.ACCOUNT_CAN_NOT_DELETE_CHECK_ACCOUNT_BALANCE);
        }

        checkingAccount.setAccountStatus(AccountStatus.PASSIVE);
        checkingAccount.setDeleted(true);
        checkingAccount.setDeletedAt(new Date());
        checkingAccount.setDeletedBy(loggedUserId.toString());
        checkingAccountRepository.save(checkingAccount);

        log.info("Checking account soft deleted successfully by id -> {}", checkingAccount.getId());

        return checkingAccount.isDeleted();
    }

    @Override
    public Object deleteDepositAccountById(Long id, HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        DepositAccount depositAccount = depositAccountRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException(Constants.ErrorMessage.ACCOUNT_NOT_FOUND));

        if (!(depositAccount.getUser().getId().equals(loggedUserId))) {
            throw new BusinessServiceOperationException.LoggerCanOnlyDeleteOwnAccountException(Constants.ErrorMessage.LOGGER_CAN_ONLY_DELETE_OWN_ACCOUNT);
        }
        if (depositAccount.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessServiceOperationException.AccountCanNotDeleteException(Constants.ErrorMessage.ACCOUNT_CAN_NOT_DELETE_CHECK_ACCOUNT_BALANCE);
        }

        depositAccount.setAccountStatus(AccountStatus.PASSIVE);
        depositAccount.setDeleted(true);
        depositAccount.setDeletedAt(new Date());
        depositAccount.setDeletedBy(loggedUserId.toString());
        depositAccountRepository.save(depositAccount);

        log.info("Checking account soft deleted successfully by id -> {}", depositAccount.getId());

        return depositAccount.isDeleted();
    }


    private User findByIdAndIsDeletedAndStatus(HttpServletRequest httpServletRequest) {

        Long userId = customJWTAuthenticationFilter
                .findUserId(customJWTAuthenticationFilter.findToken(httpServletRequest));
        return userRepository
                .findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException(Constants.ErrorMessage.USER_NOT_FOUND));
    }

}
