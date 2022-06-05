package com.example.finalprojectaozcann.repository;

import com.example.finalprojectaozcann.model.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    Collection<TransferHistory> findAllBySenderId(Long cardId);

    Collection<TransferHistory> findAllByIsScheduled(boolean isScheduled);
}
