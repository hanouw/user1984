package com.jpa.user1984.service;

import com.jpa.user1984.domain.*;
import com.jpa.user1984.dto.StoreReviewDTO;
import com.jpa.user1984.dto.StoreReviewForm;
import com.jpa.user1984.repository.PaymentMemRepository;
import com.jpa.user1984.repository.StoreRepository;
import com.jpa.user1984.repository.StoreReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreReviewService {

    private final StoreReviewRepository storeReviewRepository;
    private final StoreRepository storeRepository;
    private final PaymentMemRepository paymentMemRepository;

    // 서점리뷰 목록 조회
    public List<StoreReviewDTO> findListByStoreId(Long storeId) {
        List<StoreReview> all = storeReviewRepository.findStoreReviewList(storeId);
        System.out.println("all = " + all);//all까지는 불러옴
        List<StoreReviewDTO> list = all.stream()
                .map(b -> new StoreReviewDTO(b))
                .collect(Collectors.toList());
        System.out.println("list = " + list);
        return list;
    }

    // 서점리뷰 등록
    // 댓글 등록 : save()
    public Long storeReviewAdd(StoreReviewForm storeReviewForm, Member member) {
        StoreReview storeReview = storeReviewForm.toEntity();
        Store store = storeRepository.findById(storeReviewForm.getStoreId()).orElse(null);
        storeReview.setStore(store); // Comment Entity에 Board객체 채우기
        storeReview.setMember(member);
        storeReview.setStoreReviewStatus(StoreReviewStatus.ON);
        PaymentMem result =
            paymentMemRepository.findByMember_UserNoAndAndStore_StoreId(member.getUserNo(), store.getStoreId()).orElse(null);
        if (result != null) {
            result.setStoreReviewStatus(StoreReviewStatus.ON);
        }
        StoreReview saved = null; // 저장된 entity를 담아줄 변수 미리 선언
        // 일반 댓글 : id=null, storeReviewPosition=0, step=0, level=0
        if (storeReview.getStoreReviewPosition() == 0) {
            saved = storeReviewRepository.save(storeReview);// 저장!
            saved.setStoreReviewPosition(saved.getStoreReviewId()); // storeReviewPosition를 id값으로 수정 (그룹핑)
        }else { // 대댓글 : id=null, storeReviewPosition=댓글 다는 대상 id, step=0 이상, level=0 이상
            // 이전 댓글들 모두 step + 1하기 (밑으로 밀어서 공간 만들기)
            int updatedCount = storeReviewRepository.updateStep(storeReview.getStoreReviewPosition(), storeReview.getStep());
            log.info("***** CommentService - Add - updatedCount : {}", updatedCount);
            storeReview.setStep(storeReview.getStep() + 1);
            saved = storeReviewRepository.save(storeReview);
        }
        log.info("***** CommentService - Add - saved : {}", saved);
        return saved.getStoreReviewId(); // 저장된 댓글의 고유번호 리턴
    }

//    // 댓글 등록 : save()
//    public Long addComment(CommentForm commentForm) {
//        Comment cmt = commentForm.toEntity();
//        Board board = boardRepository.findById(commentForm.getBoardId()).orElse(null);
//        cmt.setBoard(board); // Comment Entity에 Board객체 채우기
//        Comment saved = null; // 저장된 entity를 담아줄 변수 미리 선언
//        // 일반 댓글 : id=null, refId=0, step=0, level=0
//        if (cmt.getRefId() == 0) {
//            saved = commentRepository.save(cmt);// 저장!
//            saved.setRefId(saved.getId()); // refId를 id값으로 수정 (그룹핑)
//        }else { // 대댓글 : id=null, refId=댓글 다는 대상 id, step=0 이상, level=0 이상
//            // 이전 댓글들 모두 step + 1하기 (밑으로 밀어서 공간 만들기)
//            int updatedCount = commentRepository.updateStep(cmt.getRefId(), cmt.getStep());
//            log.info("***** CommentService - Add - updatedCount : {}", updatedCount);
//            cmt.setStep(cmt.getStep() + 1);
//            cmt.setLevel(cmt.getLevel() + 1);
//            saved = commentRepository.save(cmt);
//        }
//        log.info("***** CommentService - Add - saved : {}", saved);
//        return saved.getId(); // 저장된 댓글의 고유번호 리턴
//    }

    // 서점리뷰 조회 (한개 조회)
    public StoreReview getOneStoreReview(Long storeReviewId) {

        StoreReview storeReview = storeReviewRepository.findById(storeReviewId).orElse(null);

        return storeReview;
    }
}
