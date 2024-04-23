package com.jpa.user1984.service;

import com.jpa.user1984.domain.*;
import com.jpa.user1984.dto.BookReviewDTO;
import com.jpa.user1984.dto.BookReviewForm;
import com.jpa.user1984.repository.BookRepository;
import com.jpa.user1984.repository.BookReviewRepository;
import com.jpa.user1984.repository.PaymentBookHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final PaymentBookHistoryRepository paymentBookHistoryRepository;

    // 도서 리뷰 목록 조회
    public List<BookReviewDTO> findListByBookId(Long bookId) {
        List<BookReview> all = bookReviewRepository.findBookReviewList(bookId);
        System.out.println("all = " + all);//all까지는 불러옴
        List<BookReviewDTO> list = all.stream()
                .map(b -> new BookReviewDTO(b))
                .collect(Collectors.toList());
        System.out.println("list = " + list);
        return list;
    }

    // 도서 댓글 등록 : save()
    public Long bookReviewAdd(BookReviewForm bookReviewForm, Member member) {
        BookReview bookReview = bookReviewForm.toEntity();
        Book book = bookRepository.findById(bookReviewForm.getBookId()).orElse(null);
        bookReview.setBook(book); // Comment Entity에 Board객체 채우기
        bookReview.setMember(member);
        bookReview.setBookReviewStatus(BookReviewStatus.ON);
        PaymentBookHistory result =
            paymentBookHistoryRepository.findPaymentBookHistoryByPaymentBook_Member_UserNoAndBook_BookId(member.getUserNo(), book.getBookId()).orElse(null);
        if (result != null) {
            result.setBookReviewStatus(BookReviewStatus.ON);
        }
        BookReview saved = null; // 저장된 entity를 담아줄 변수 미리 선언
        // 일반 댓글 : id=null, bookReviewPosition=0, step=0, level=0
        if (bookReview.getBookReviewPosition() == 0) {
            saved = bookReviewRepository.save(bookReview);// 저장!
            saved.setBookReviewPosition(saved.getBookReviewId()); // bookReviewPosition를 id값으로 수정 (그룹핑)
        }else { // 대댓글 : id=null, bookReviewPosition=댓글 다는 대상 id, step=0 이상, level=0 이상
            // 이전 댓글들 모두 step + 1하기 (밑으로 밀어서 공간 만들기)
            int updatedCount = bookReviewRepository.updateStep(bookReview.getBookReviewPosition(), bookReview.getStep());
            log.info("***** CommentService - Add - updatedCount : {}", updatedCount);
            bookReview.setStep(bookReview.getStep() + 1);
            saved = bookReviewRepository.save(bookReview);
        }
        log.info("***** CommentService - Add - saved : {}", saved);
        return saved.getBookReviewId(); // 저장된 댓글의 고유번호 리턴
    }

    // 서점리뷰 조회 (한개 조회)
    public BookReview getOnebookReview(Long bookReviewId) {

        BookReview bookReview = bookReviewRepository.findById(bookReviewId).orElse(null);

        return bookReview;
    }
}
