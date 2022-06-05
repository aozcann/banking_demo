package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.converter.CardConverter;
import com.example.finalprojectaozcann.converter.UserConverter;
import com.example.finalprojectaozcann.exception.BaseException;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.*;
import com.example.finalprojectaozcann.model.enums.*;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.CreateUserResponse;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import com.example.finalprojectaozcann.repository.*;
import com.example.finalprojectaozcann.service.UserService;
import com.example.finalprojectaozcann.utils.JWTDecodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CheckingAccountRepository checkingAccountRepository;
    private final BankAccountConverter bankAccountConverter;
    private final JWTDecodeUtil jwtDecodeUtil;
    private final BankCardRepository bankCardRepository;
    private final CardConverter cardConverter;
    private final DebitCardRepository debitCardRepository;
    private final DepositAccountRepository depositAccountRepository;

    public CreateUserResponse create(CreateUserRequest request, HttpServletRequest httpServletRequest) {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        User user = userConverter.toCreateUser(request, loggedUserId);
        userRepository.save(user);
        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(Currency.TRY, user);
        checkingAccountRepository.save(checkingAccount);
        BankCard bankCard = cardConverter.toCreateBankCard(user, checkingAccount);
        bankCardRepository.save(bankCard);
        log.info("User created successfully by id -> {}", user.getId());
        log.info("User's first checking account is created by id -> {} ", checkingAccount.getId());
        log.info("User's first bank card is created by id -> {} ", bankCard.getId());

        return new CreateUserResponse(user.getId());
    }

    @Override
    public Collection<GetUserResponse> getAllUser() {

        List<GetUserResponse> userResponseList = userRepository.findAllByIsDeleted(false)
                .stream()
                .map(userConverter::toGetUserResponse)
                .toList();
        log.info("User list returned successfully.");
        return userResponseList;
    }

    @Override
    public GetUserResponse updateUser(UpdateUserRequest request,
                                      Long id, HttpServletRequest httpServletRequest) throws BaseException {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        User user = userRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .UserNotFoundException(Constants.ErrorMessage.USER_NOT_FOUND));
        User updateUser = userConverter.toUpdateUser(request, user, loggedUserId);
        userRepository.save(updateUser);
        log.info("User updated successfully by id -> {}", user.getId());
        return userConverter.toGetUserResponse(user);
    }

    @Override
    public GetUserResponse getUser(Long id) throws BaseException {
        User user = userRepository
                .findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .UserNotFoundException(Constants.ErrorMessage.USER_NOT_FOUND));
        log.info("User returned successfully by id -> {}", user.getId());
        return userConverter.toGetUserResponse(user);
    }

    @Override
    public boolean deleteUserById(Long id, boolean isHardDeleted,
                                  HttpServletRequest httpServletRequest) throws BaseException {

        Long loggedUserId = jwtDecodeUtil.findUserIdFromJwt(httpServletRequest);

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new BusinessServiceOperationException
                        .UserNotFoundException(Constants.ErrorMessage.USER_NOT_FOUND));

        Collection<DepositAccount> allDepositAccounts = depositAccountRepository.findAllByUser(user);
        Collection<CheckingAccount> allCheckingAccounts = checkingAccountRepository.findAllByUser(user);
        Collection<DebitCard> allDebitCards = debitCardRepository.findAllByUser(user);
        Collection<BankCard> allBankCards = bankCardRepository.findAllByUser(user);
        checkAllAccountBalanceAndCardDebtIsZero(allDepositAccounts,
                allCheckingAccounts, allDebitCards, loggedUserId, allBankCards);

        if (isHardDeleted) {
            userRepository.delete(user);
            log.info("User hard deleted successfully.");
            return true;
        }
        if (user.isDeleted()) {
            log.error("User id:{} already deleted.", id);
            throw new BusinessServiceOperationException
                    .UserAlreadyDeletedException(Constants.ErrorMessage.USER_ALREADY_DELETED);
        }
        user.setDeleted(true);
        user.setStatus(UserStatus.PASSIVE);
        user.setDeletedAt(new Date());
        user.setDeletedBy(loggedUserId.toString());
        bankCardRepository.saveAll(allBankCards);
        debitCardRepository.saveAll(allDebitCards);
        depositAccountRepository.saveAll(allDepositAccounts);
        checkingAccountRepository.saveAll(allCheckingAccounts);
        userRepository.save(user);
        log.info("User soft deleted successfully by id -> {}", user.getId());

        return user.isDeleted();
    }

    @Override
    public GenerateAdminUserResponse generateAdminUser() throws BaseException {

        if (!(userRepository.findAll().isEmpty())) {
            throw new BusinessServiceOperationException
                    .AdminAlreadyGeneratedException(Constants.ErrorMessage.ADMIN_ALREADY_GENERATED);
        }
        User user = new User();

        user.setName("admin");
        user.setSurname("NORMA");
        user.setIdentityNumber(10_000_000_000L);
        user.setBirthday(LocalDate.now());
        user.setEmail("admin@admin.com");
        user.setAddress("Istanbul");
        user.setPhoneNumber("08502117373");
        user.setStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.BUSINESS);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("Norm@.1"));

        Role role = new Role();
        role.setName(RoleType.ADMIN.toString());
        user.setRoles(new HashSet<>(List.of(role)));

        userRepository.save(user);
        log.info("Admın generated successfully");

        return new GenerateAdminUserResponse(user.getIdentityNumber(), "Norm@.1", user.getRoles());
    }

    public void checkAllAccountBalanceAndCardDebtIsZero(Collection<DepositAccount> depositAccounts,
                                                        Collection<CheckingAccount> checkingAccounts,
                                                        Collection<DebitCard> debitCards, Long loggedUserId,
                                                        Collection<BankCard> bankCards) {
        BigDecimal totalBalance = BigDecimal.valueOf(0);

        for (CheckingAccount checkingAccount : checkingAccounts) {
            totalBalance.add(checkingAccount.getBalance());
            checkingAccount.setDeleted(true);
            checkingAccount.setAccountStatus(AccountStatus.PASSIVE);
            checkingAccount.setDeletedAt(new Date());
            checkingAccount.setDeletedBy(loggedUserId.toString());
        }
        if (totalBalance.compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessServiceOperationException
                    .UserCanNotDeletedException(Constants.ErrorMessage.USER_CAN_NOT_DELETE_CHECK_CHECKING_ACCOUNT_BALANCE);
        } else {
            for (DepositAccount depositAccount : depositAccounts) {
                totalBalance.add(depositAccount.getBalance());
                depositAccount.setDeleted(true);
                depositAccount.setAccountStatus(AccountStatus.PASSIVE);
                depositAccount.setDeletedAt(new Date());
                depositAccount.setDeletedBy(loggedUserId.toString());
            }

            if (totalBalance.compareTo(BigDecimal.ZERO) > 0) {
                throw new BusinessServiceOperationException
                        .UserCanNotDeletedException(Constants.ErrorMessage.USER_CAN_NOT_DELETE_CHECK_DEPOSIT_ACCOUNT_BALANCE);
            } else {
                for (DebitCard debitCard : debitCards) {
                    totalBalance.add(debitCard.getDept());
                    debitCard.setDeleted(true);
                    debitCard.setCardStatus(CardStatus.PASSIVE);
                    debitCard.setDeletedAt(new Date());
                    debitCard.setDeletedBy(loggedUserId.toString());
                }

                if (totalBalance.compareTo(BigDecimal.ZERO) > 0) {
                    throw new BusinessServiceOperationException
                            .UserCanNotDeletedException(Constants.ErrorMessage.USER_CAN_NOT_DELETE_CHECK_DEBIT_CARD_DEBT);
                }
                for (BankCard bankCard : bankCards) {
                    bankCard.setDeleted(true);
                    bankCard.setCardStatus(CardStatus.PASSIVE);
                    bankCard.setDeletedAt(new Date());
                    bankCard.setDeletedBy(loggedUserId.toString());
                }
            }
        }
    }
}