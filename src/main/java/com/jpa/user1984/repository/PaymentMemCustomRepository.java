package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentMem;
import com.jpa.user1984.dto.PageRequestDTO;

import java.util.List;

public interface PaymentMemCustomRepository {
    List<PaymentMem> findMembershipListByUserNo(Long storeId, PageRequestDTO pageRequestDTO);

    Long countMembershipListByUserNo(Long storeId, PageRequestDTO pageRequestDTO);

    String searchTypeMethod(PageRequestDTO pageRequestDTO);

    PaymentMem findMembershipByOrderMembershipId(Long orderMembershipId);
}
