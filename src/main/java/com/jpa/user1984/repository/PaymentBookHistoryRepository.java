package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentBookHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentBookHistoryRepository extends JpaRepository<PaymentBookHistory, Long> {
}
