package com.jpa.user1984.repository;

import com.jpa.user1984.domain.StoreReview;
import com.jpa.user1984.dto.PageRequestDTO;

import java.util.List;

public interface StoreReviewCustomRepository {

    List<StoreReview> findStoreReviewList(Long storeId);
    int updateStep(Long storeReviewId, Integer step);
}
