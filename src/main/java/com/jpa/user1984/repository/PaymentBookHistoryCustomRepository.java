package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentBookHistory;
import com.jpa.user1984.dto.PageRequestDTO;

import java.util.List;

public interface PaymentBookHistoryCustomRepository {
    // storeId로 주문 목록 조회 판매자 ver
    List<PaymentBookHistory> findListByUserNo(Long userNo, PageRequestDTO pageRequestDTO);

    Long countHistoryListByUserNo(Long userNo, PageRequestDTO pageRequestDTO);

    String searchTypeMethod(PageRequestDTO pageRequestDTO);

    PageRequestDTO bindingMethod(PageRequestDTO pageRequestDTO);
}
