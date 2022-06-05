package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {

    Optional<CheckingAccount> findByAccountNumberAndIsDeleted(String accountNumber, boolean isDeleted);

    Optional<CheckingAccount> findByIbanAndIsDeletedAndAccountStatus(String iban, boolean isDeleted, AccountStatus accountStatus);

    Collection<CheckingAccount> findAllByUser(User user);

    Optional<CheckingAccount> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
