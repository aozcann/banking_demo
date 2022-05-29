package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.converter.UserConverter;
import com.example.finalprojectaozcann.exception.BaseException;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.model.enums.UserType;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.CreateUserResponse;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.UserRepository;
import com.example.finalprojectaozcann.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public CreateUserResponse create(CreateUserRequest request) {
        User user = userConverter.toCreateUser(request);
        userRepository.save(user);

        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(Currency.TRY, user);
        checkingAccountRepository.save(checkingAccount);
        log.info("User created successfully by id -> {}", user.getId());
        log.info("User's first checking account is created by id -> {} ", checkingAccount.getId());
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
    public GetUserResponse updateUser(UpdateUserRequest request, Long id) throws BaseException {
        User user = userRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found"));
        User updateUser = userConverter.toUpdateUser(request, user);
        userRepository.save(updateUser);
        log.info("User updated successfully by id -> {}", user.getId());
        return userConverter.toGetUserResponse(user);
    }

    @Override
    public GetUserResponse getUser(Long id) throws BaseException {
        User user = userRepository
                .findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found"));
        log.info("User returned successfully by id -> {}", user.getId());
        return userConverter.toGetUserResponse(user);
    }

    @Override
    public boolean deleteUserById(Long id, boolean isHardDeleted) throws BaseException {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found."));
        if (isHardDeleted) {
            userRepository.delete(user);
            log.info("User hard deleted successfully.");
            return true;
        }
        if (user.isDeleted()) {
            log.error("User id:{} already deleted.", id);
            throw new BusinessServiceOperationException.UserAlreadyDeletedException("User already deleted.");
        }
        user.setDeleted(true);
        //TODO rol ve account delete eklenecek
        user.setDeletedAt(new Date());
        user.setDeletedBy("AhmetOzcan");
        userRepository.save(user);
        log.info("User soft deleted successfully by id -> {}", user.getId());

        return user.isDeleted();
    }

    @Override
    public GenerateAdminUserResponse generateAdminUser() throws BaseException {

        if (!(userRepository.findAll().isEmpty())) {
            throw new BusinessServiceOperationException.AdminAlreadyGeneratedException("Admin already generated.");
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

        return new GenerateAdminUserResponse(user.getPassword(), user.getRoles());
    }
}

