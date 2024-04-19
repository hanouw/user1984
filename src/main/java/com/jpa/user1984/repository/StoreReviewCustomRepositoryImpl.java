package com.jpa.user1984.repository;

import com.jpa.user1984.domain.StoreReview;
import com.jpa.user1984.dto.PageRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreReviewCustomRepositoryImpl implements StoreReviewCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StoreReview> findStoreReviewList(Long storeId) {
        List<StoreReview> list = em.createQuery("select s from StoreReview s where s.store.storeId = :storeId order by s.storeReviewPosition desc, s.step asc",
                        StoreReview.class)
                .setParameter("storeId", storeId)
                .getResultList();
        return list;
    }

    @Override
    public int updateStep(Long storeReviewPosition, Integer step) {
        int updatedCount = em.createQuery(
                        "update StoreReview s set s.step = s.step + 1 where storeReviewPosition = :storeReviewPosition and step > :step")
                .setParameter("storeReviewPosition", storeReviewPosition)
                .setParameter("step", step)
                .executeUpdate();
        return updatedCount;
    }

}
