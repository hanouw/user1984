package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentBookHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentBookHistoryRepository extends JpaRepository<PaymentBookHistory, Long>, PaymentBookHistoryCustomRepository {
    Optional<PaymentBookHistory> findPaymentBookHistoryByPaymentBook_Member_UserNoAndBook_BookId(Long userNo, Long BookId);
}
