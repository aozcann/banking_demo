package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.DebitCard;
import com.example.finalprojectaozcann.model.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

    Optional<DebitCard> findByCardNumberAndCardType(String cardNumber, CardType cardType);
}
