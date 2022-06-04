package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {

    Optional<DepositAccount> findByIbanAndIsDeletedAndAccountStatus(String iban, boolean isDeleted, AccountStatus accountStatus);

    Collection<DepositAccount> findAllByUser(User user);

    Optional<DepositAccount> findByIdAndIsDeleted(Long id, boolean b);
}
