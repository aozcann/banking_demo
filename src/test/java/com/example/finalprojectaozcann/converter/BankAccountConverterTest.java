package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.*;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
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
class BankAccountConverterTest {

    @InjectMocks
    BankAccountConverter bankAccountConverter;

    @Test
    void should_returnCheckingAccount_when_toCreateCheckingAccountMethod() {

        String accountNumber = "32344479398928916";
        String iban = "TR330006132344479398928916";
        User user = createUser();

        CheckingAccount exceptedCheckingAccount = new CheckingAccount();
        exceptedCheckingAccount.setAccountType(AccountType.CHECKING);
        exceptedCheckingAccount.setAccountNumber(accountNumber);
        exceptedCheckingAccount.setIban(iban);
        exceptedCheckingAccount.setCurrency(Currency.TRY);
        exceptedCheckingAccount.setAccountStatus(AccountStatus.ACTIVE);
        exceptedCheckingAccount.setUser(user);
        exceptedCheckingAccount.setCreatedBy(user.getId().toString());
        exceptedCheckingAccount.setCreatedAt(new Date());

        CheckingAccount actualCheckingAccount = bankAccountConverter.toCreateCheckingAccount(Currency.TRY, user);

        actualCheckingAccount.setAccountNumber(accountNumber);
        actualCheckingAccount.setIban(iban);

        assertThat(exceptedCheckingAccount).isEqualTo(actualCheckingAccount);
    }

    @Test
    void should_returnDepositAccount_when_toCreateDepositAccountMethod() {

        String accountNumber = "32344479398928916";
        String iban = "TR330006132344479398928916";
        User user = createUser();

        CreateDepositAccountRequest request = new CreateDepositAccountRequest(Currency.TRY, Maturity.DAILY);

        DepositAccount expectedDepositAccount = new DepositAccount();
        expectedDepositAccount.setAccountType(AccountType.DEPOSIT);
        expectedDepositAccount.setAccountNumber(accountNumber);
        expectedDepositAccount.setIban(iban);
        expectedDepositAccount.setCurrency(Currency.TRY);
        expectedDepositAccount.setAccountStatus(AccountStatus.ACTIVE);
        expectedDepositAccount.setUser(user);
        expectedDepositAccount.setCreatedBy(user.getId().toString());
        expectedDepositAccount.setCreatedAt(new Date());
        expectedDepositAccount.setMaturity(Maturity.DAILY);
        expectedDepositAccount.setBalance(BigDecimal.ZERO);
        expectedDepositAccount.setInterestRate(null);

        DepositAccount actualDepositAccount = bankAccountConverter.toCreateDepositAccount(request, user);

        actualDepositAccount.setIban(iban);
        actualDepositAccount.setAccountNumber(accountNumber);
        assertThat(expectedDepositAccount).isEqualTo(actualDepositAccount);
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
}
