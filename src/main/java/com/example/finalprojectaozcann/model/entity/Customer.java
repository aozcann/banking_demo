package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseExtendedEntity;
import com.example.finalprojectaozcann.model.enums.CustomerStatus;
import com.example.finalprojectaozcann.model.enums.CustomerType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseExtendedEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Transient
    private String fullName = name + " " + surname;
    @Column(nullable = false)
    private Long identityNumber;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.ACTIVE;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;


    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    private Set<BankAccount> bankAccounts = new HashSet<>();

}
