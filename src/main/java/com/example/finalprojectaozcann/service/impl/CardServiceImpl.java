package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.CardConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import com.example.finalprojectaozcann.repository.BankCardRepository;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DebitCardRepository;
import com.example.finalprojectaozcann.repository.UserRepository;
import com.example.finalprojectaozcann.service.CardService;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardConverter cardConverter;
    private final BankCardRepository bankCardRepository;
    private final JWTDecodeUtil jwtDecodeUtil;
    private final CheckingAccountRepository checkingAccountRepository;
    private final UserRepository userRepository;
    private final DebitCardRepository debitCardRepository;

    @Override
    public GetBankCardResponse createBankCard(CreateCardRequest request, HttpServletRequest httpServletRequest) {

        if (bankCardRepository.findByAndCheckingAccount_AccountNumber(request.accountNumber()).isPresent()) {
            throw new BusinessServiceOperationException.BankCardAlreadyExist("One checking account can only have a bank card");
        }

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        User user = findByIdAndIsDeleted(userId);
        CheckingAccount checkingAccount = findByAccountNumberAndIsDeleted(request.accountNumber());
        BankCard bankCard = cardConverter.toCreateBankCard(user, checkingAccount);
        bankCardRepository.save(bankCard);
        return cardConverter.toBankCardResponse(bankCard, checkingAccount.getBalance());
    }

    @Override
    public GetDebitCardResponse createDebitCard(CreateCardRequest request, HttpServletRequest httpServletRequest) {

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        User user = findByIdAndIsDeleted(userId);
        CheckingAccount checkingAccount = findByAccountNumberAndIsDeleted(request.accountNumber());
        DebitCard debitCard = cardConverter.toCreateDebitCard(user, checkingAccount);
        debitCardRepository.save(debitCard);
        return cardConverter.toDebitCardResponse(debitCard);
    }

    @Override
    public GetDebitCardDeptInquiryResponse getInquiryDebitCard(DebitCardDeptInquiryRequest request, HttpServletRequest httpServletRequest) {
        DebitCard debitCard = debitCardRepository.findByCardNumberAndIsDeleted(request.debitCardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException.DebitCardNotFoundException("Debit card not found"));
        return cardConverter.toGetDebitCardDeptInquiryResponse(debitCard);
    }


    public User findByIdAndIsDeleted(Long userId) {
        return userRepository.findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found"));
    }

    public CheckingAccount findByAccountNumberAndIsDeleted(String accountNumber) {
        return checkingAccountRepository
                .findByAccountNumberAndIsDeleted(accountNumber, false)
                .orElseThrow(() -> new BusinessServiceOperationException.AccountNotFoundException("Account not found"));
    }


}
