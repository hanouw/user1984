package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentMem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMemRepository extends JpaRepository<PaymentMem, Long>, PaymentMemCustomRepository {
}
