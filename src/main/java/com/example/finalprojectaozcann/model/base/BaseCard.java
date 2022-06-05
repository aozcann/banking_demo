package com.example.finalprojectaozcann.model.base;

import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.CardStatus;
import com.example.finalprojectaozcann.model.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class BaseCard extends BaseExtendedEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private String ccv;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseCard baseCard = (BaseCard) o;
        return Objects.equals(name, baseCard.name) && Objects.equals(surname, baseCard.surname) && cardType == baseCard.cardType && Objects.equals(cardNumber, baseCard.cardNumber) && Objects.equals(expiryDate, baseCard.expiryDate) && Objects.equals(ccv, baseCard.ccv) && Objects.equals(password, baseCard.password) && cardStatus == baseCard.cardStatus && Objects.equals(user, baseCard.user);
    }

}
