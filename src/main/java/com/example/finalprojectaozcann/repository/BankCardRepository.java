package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.BankCard;
import com.example.finalprojectaozcann.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    Optional<BankCard> findByAndCheckingAccount_AccountNumber(String accountNumber);

    Optional<BankCard> findByCardNumberAndIsDeleted(String cardNumber, boolean b);

    Collection<BankCard> findAllByUser(User user);
}
