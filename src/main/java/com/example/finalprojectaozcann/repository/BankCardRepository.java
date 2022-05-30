package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {


    Optional<BankCard> findByAndCheckingAccount_AccountNumber(String accountNumber);
}
