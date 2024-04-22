package com.jpa.user1984.repository;

import com.jpa.user1984.domain.BookReview;
import com.jpa.user1984.domain.StoreReview;

import java.util.List;

public interface BookReviewCustomRepository {

    List<BookReview> findBookReviewList(Long bookId);
    int updateStep(Long bookReviewId, Integer step);
}
