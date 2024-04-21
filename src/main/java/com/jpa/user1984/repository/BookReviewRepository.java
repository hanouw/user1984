package com.jpa.user1984.repository;

import com.jpa.user1984.domain.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long>,BookReviewCustomRepository {

    int updateStep(Long bookId, Integer step);
}