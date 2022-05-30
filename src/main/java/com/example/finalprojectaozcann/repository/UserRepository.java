package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdentityNumberAndStatus(Long identity, UserStatus status);

    Collection<User> findAllByIsDeleted(boolean isDeleted);

    Optional<User> findByIdAndIsDeleted(Long id, boolean isDeleted);

}
