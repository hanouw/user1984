package com.jpa.user1984.service;

import com.jpa.user1984.dto.PaymentBookForm;
import com.jpa.user1984.domain.PaymentBook;
import com.jpa.user1984.domain.PaymentBookHistory;
import com.jpa.user1984.domain.PaymentBookStatus;
import com.jpa.user1984.repository.BookRepository;
import com.jpa.user1984.repository.MemberRepository;
import com.jpa.user1984.repository.PaymentBookHistoryRepository;
import com.jpa.user1984.repository.PaymentBookRepository;
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
public class PaymentBookService {

    private final PaymentBookRepository paymentBookRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final PaymentBookHistoryRepository paymentBookHistoryRepository;
    private final EntityManager entityManager;

    // 책 주문 등록
    public void saveOrder(Long userNo, PaymentBookForm paymentBookForm) {

        log.info("**************PaymentBookService saveOrder 실행");

        PaymentBook order = new PaymentBook();
        order.setOrderBookId(paymentBookForm.getOrderBookId());
        order.setMember(memberRepository.findById(userNo).orElse(null)); // 추후 수정 필요
        order.setOrderBookMethod(paymentBookForm.getOrderBookMethod());
        order.setPaymentBookStatus(PaymentBookStatus.COMPLETE);
        PaymentBook saved = paymentBookRepository.save(order);
        entityManager.flush();
        log.info("**************PaymentBookService paymentBookRepository.save 성공 PaymentBook:{}", saved);

        // 책 주문 내역 등록
        List<PaymentBookHistory> histories = new ArrayList<>();
        for (String bookList : paymentBookForm.getSelectedBooks()) {
            PaymentBookHistory history = new PaymentBookHistory();
            long bookId = Long.parseLong(bookList);
            log.info("**************PaymentBookService bookId:{}", bookId);
            PaymentBook findPaymentBook = paymentBookRepository.findById(saved.getOrderBookNo()).orElse(null);
            log.info("**************PaymentBookService findPaymentBook:{}", findPaymentBook);
            history.setPaymentBook(findPaymentBook);
            history.setBook(bookRepository.findById(bookId).orElse(null));
            histories.add(history);
            paymentBookHistoryRepository.save(history);
        }


    }
}
