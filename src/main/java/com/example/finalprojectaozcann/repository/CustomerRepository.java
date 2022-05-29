package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.Customer;
import com.example.finalprojectaozcann.model.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByIdentityNumberAndStatus(Long identity, CustomerStatus status);

    Collection<Customer> findAllByIsDeleted(boolean isDeleted);

    Optional<Customer> findByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<Customer> findByIdAndIsDeletedAndStatus(Long id, boolean isDeleted,CustomerStatus status);
}
