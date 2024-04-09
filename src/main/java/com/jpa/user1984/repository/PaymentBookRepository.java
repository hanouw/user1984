package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentBookRepository extends JpaRepository<PaymentBook, Long> {
}
