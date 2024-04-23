package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentMem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMemRepository extends JpaRepository<PaymentMem, Long>, PaymentMemCustomRepository {
    Optional<PaymentMem> findPaymentMemByMember_UserNoAndAndStore_StoreId(Long userNo, Long storeId);
}
