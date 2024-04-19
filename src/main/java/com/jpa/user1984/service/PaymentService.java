package com.jpa.user1984.service;

import com.jpa.user1984.domain.*;
import com.jpa.user1984.dto.PaymentBookForm;
import com.jpa.user1984.dto.PaymentMemForm;
import com.jpa.user1984.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentBookRepository paymentBookRepository;
    private final PaymentBookHistoryRepository paymentBookHistoryRepository;
    private final PaymentMemRepository paymentMemRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    // 책 주문 등록
    public Long saveBookOrder(Long userNo, PaymentBookForm paymentBookForm) {

        log.info("**************PaymentService saveBookOrder 실행");

        PaymentBook order = new PaymentBook();
        order.setOrderBookId(paymentBookForm.getOrderBookId());
        order.setMember(memberRepository.findById(userNo).orElse(null));
        order.setOrderBookMethod(paymentBookForm.getOrderBookMethod());
        order.setPaymentBookStatus(PaymentBookStatus.COMPLETE);
        order.setTotalAmount(paymentBookForm.getTotalAmount());
        PaymentBook saved = paymentBookRepository.save(order);
        entityManager.flush();

        // 책 주문 내역 등록
        List<PaymentBookHistory> histories = new ArrayList<>();
        for (String bookList : paymentBookForm.getSelectedBooks()) {
            PaymentBookHistory history = new PaymentBookHistory();
            long bookId = Long.parseLong(bookList);
            PaymentBook findPaymentBook = paymentBookRepository.findById(saved.getOrderBookNo()).orElse(null);
            history.setPaymentBook(findPaymentBook);
            Book findBook = bookRepository.findById(bookId).orElse(null);
            history.setBook(findBook);
            history.setPrice(findBook.getBookEbookPrice());
            history.setBookReviewStatus(BookReviewStatus.OFF);

            histories.add(history);
            PaymentBookHistory save = paymentBookHistoryRepository.save(history);
            log.info("-----PaymentBookHistory 저장성공 OrderBookHistoryId:{}", save.getOrderBookHistoryId());
        }
        log.info("**************PaymentService.saveBookOrder 성공 PaymentBook:{}", saved);
        return saved.getOrderBookId(); //2024040100000 리턴
    }

    // 구독권 주문 등록
    public Long saveMembershipOrder(Long userNo, PaymentMemForm paymentMemForm) {
        log.info("**************PaymentService saveMembershipOrder 실행");

        PaymentMem order = new PaymentMem();
        order.setOrderMembershipId(paymentMemForm.getOrderMembershipId());
        order.setOrderMembershipMethod(paymentMemForm.getOrderMembershipMethod());
        order.setMember(memberRepository.findById(userNo).orElse(null));
        order.setStore(storeRepository.findById(paymentMemForm.getStoreId()).orElse(null));
        order.setPaymentMemStatus(PaymentMemStatus.COMPLETE);
        order.setPrice(paymentMemForm.getPrice());
        order.setStoreReviewStatus(StoreReviewStatus.OFF);

        PaymentMem saved = paymentMemRepository.save(order);
        log.info("**************PaymentService.saveMembershipOrder 성공 PaymentMem:{}", saved);
        return saved.getOrderMembershipId();//2024040100000 리턴
    }

}
