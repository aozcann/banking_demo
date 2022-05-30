package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {

    Optional<CheckingAccount> findByAccountNumberAndIsDeleted(String accountNumber, boolean isDeleted);

}
