package com.jpa.user1984.repository;

import com.jpa.user1984.domain.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreReviewRepository extends JpaRepository<StoreReview, Long>,StoreReviewCustomRepository {

    int updateStep(Long storeId, Integer step);
}