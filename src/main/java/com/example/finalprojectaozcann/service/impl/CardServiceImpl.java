package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.converter.CardConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.request.CardExtractRequest;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetCardExtractResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import com.example.finalprojectaozcann.repository.*;
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
    private final TransferHistoryRepository transferHistoryRepository;

    @Override
    public GetBankCardResponse createBankCard(CreateCardRequest request, HttpServletRequest httpServletRequest) {

        if (bankCardRepository.findByAndCheckingAccount_AccountNumber(request.accountNumber()).isPresent()) {
            throw new BusinessServiceOperationException
                    .BankCardAlreadyExist(Constants.ErrorMessage.ONE_CHECKING_ACCOUNT_CAN_ONLY_HAVE_A_BANK_CARD);
        }

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount checkingAccount = findByAccountNumberAndIsDeleted(request.accountNumber());

        checkLoggerEqualCardOwner(userId, checkingAccount.getUser().getId());

        User user = findByIdAndIsDeleted(userId);
        BankCard bankCard = cardConverter.toCreateBankCard(user, checkingAccount);
        bankCardRepository.save(bankCard);
        return cardConverter.toBankCardResponse(bankCard, checkingAccount.getBalance());
    }

    @Override
    public GetDebitCardResponse createDebitCard(CreateCardRequest request, HttpServletRequest httpServletRequest) {

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);
        CheckingAccount checkingAccount = findByAccountNumberAndIsDeleted(request.accountNumber());

        checkLoggerEqualCardOwner(userId, checkingAccount.getUser().getId());

        User user = findByIdAndIsDeleted(userId);
        DebitCard debitCard = cardConverter.toCreateDebitCard(user, checkingAccount);
        debitCardRepository.save(debitCard);
        return cardConverter.toDebitCardResponse(debitCard);
    }

    @Override
    public GetDebitCardDeptInquiryResponse getInquiryDebitCard(DebitCardDeptInquiryRequest request,
                                                               HttpServletRequest httpServletRequest) {
        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        DebitCard debitCard = debitCardRepository.findByCardNumberAndIsDeleted(request.debitCardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));

        checkLoggerEqualCardOwner(userId, debitCard.getUser().getId());


        return cardConverter.toGetDebitCardDeptInquiryResponse(debitCard);
    }

    @Override
    public GetCardExtractResponse getExtractOfDebitCard(CardExtractRequest request, HttpServletRequest httpServletRequest) {

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        DebitCard debitCard = debitCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .DebitCardNotFoundException(Constants.ErrorMessage.DEBIT_CARD_NOT_FOUND));

        checkLoggerEqualCardOwner(userId, debitCard.getUser().getId());

        return new GetCardExtractResponse(transferHistoryRepository.findAllBySenderId(debitCard.getId()));
    }

    @Override
    public GetCardExtractResponse getExtractOfBankCard(CardExtractRequest request, HttpServletRequest httpServletRequest) {

        Long userId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        BankCard bankCard = bankCardRepository.findByCardNumberAndIsDeleted(request.cardNumber(), false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .BankCardNotFoundException(Constants.ErrorMessage.BANK_CARD_NOT_FOUND));

        checkLoggerEqualCardOwner(userId, bankCard.getUser().getId());

        return new GetCardExtractResponse(transferHistoryRepository.findAllBySenderId(bankCard.getId()));
    }


    public User findByIdAndIsDeleted(Long userId) {
        return userRepository.findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .UserNotFoundException(Constants.ErrorMessage.USER_NOT_FOUND));
    }

    public CheckingAccount findByAccountNumberAndIsDeleted(String accountNumber) {
        return checkingAccountRepository
                .findByAccountNumberAndIsDeleted(accountNumber, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .AccountNotFoundException(Constants.ErrorMessage.ACCOUNT_NOT_FOUND));
    }


    private void checkLoggerEqualCardOwner(Long loggerId, Long cardOwnerId) {
        if (!(loggerId.equals(cardOwnerId))) {
            throw new BusinessServiceOperationException
                    .UserCanNotTransferException(Constants.ErrorMessage.USER_CAN_ONLY_QUERY_WITH_OWN_CARD);
        }
    }

}
