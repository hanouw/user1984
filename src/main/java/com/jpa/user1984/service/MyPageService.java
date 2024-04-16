package com.jpa.user1984.service;

import com.jpa.user1984.domain.PaymentBookHistory;
import com.jpa.user1984.domain.PaymentMem;
import com.jpa.user1984.dto.PageRequestDTO;
import com.jpa.user1984.dto.PaymentBookHistoryDTO;
import com.jpa.user1984.dto.PaymentMemDTO;
import com.jpa.user1984.repository.PaymentBookHistoryRepository;
import com.jpa.user1984.repository.PaymentMemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final PaymentBookHistoryRepository paymentBookHistoryRepository;
    private final PaymentMemRepository paymentMemRepository;

    // 도서주문내역 조회
    public List<PaymentBookHistoryDTO> findHistoryList(Long userNo, PageRequestDTO pageRequestDTO) {
        List<PaymentBookHistory> historyEntityList = paymentBookHistoryRepository.findListByUserNo(userNo, pageRequestDTO);
        List<PaymentBookHistoryDTO> list = new ArrayList<>();
        for (PaymentBookHistory orderList : historyEntityList) {
            PaymentBookHistoryDTO paymentBookHistoryDTO = new PaymentBookHistoryDTO(orderList);
            list.add(paymentBookHistoryDTO);
        }
        return list;
    }

    // 도서주문내역 개수 조회
    public Long countHistoryList(Long userNo, PageRequestDTO pageRequestDTO) {
        return paymentBookHistoryRepository.countHistoryListByUserNo(userNo, pageRequestDTO);
    }

    // 도서주문내역 상세페이지 조회
    public List<PaymentBookHistoryDTO> findBooksByOrderBookId(Long orderBookId) {
        List<PaymentBookHistory> historyEntityList = paymentBookHistoryRepository.findByOrderBookId(orderBookId);
        List<PaymentBookHistoryDTO> list = new ArrayList<>();
        for (PaymentBookHistory orderList : historyEntityList) {
            PaymentBookHistoryDTO paymentBookHistoryDTO = new PaymentBookHistoryDTO(orderList);
            list.add(paymentBookHistoryDTO);
        }
        return list;
    }

    // 구독내역 상세페이지 조회

    // 구독내역 조회
    public List<PaymentMemDTO> findMembershipList(Long userNo, PageRequestDTO pageRequestDTO) {
        List<PaymentMem> membershipEntityList = paymentMemRepository.findMembershipListByUserNo(userNo, pageRequestDTO);
        List<PaymentMemDTO> list = new ArrayList<>();
        for (PaymentMem membershipList : membershipEntityList) {
            PaymentMemDTO paymentMemDTO = new PaymentMemDTO(membershipList);
            list.add(paymentMemDTO);
        }
        return list;
    }

    // 구독내역 개수 조회
    public Long countMembershipList(Long userNo, PageRequestDTO pageRequestDTO) {
        return paymentMemRepository.countMembershipListByUserNo(userNo, pageRequestDTO);
    }
}
