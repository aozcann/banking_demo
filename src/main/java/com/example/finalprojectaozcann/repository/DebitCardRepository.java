package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

    Optional<DebitCard> findByCardNumberAndIsDeleted(String cardNumber,boolean ısDelete);
}
