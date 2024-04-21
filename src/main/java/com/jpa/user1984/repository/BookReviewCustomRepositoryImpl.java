package com.jpa.user1984.repository;

import com.jpa.user1984.domain.BookReview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookReviewCustomRepositoryImpl implements BookReviewCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BookReview> findBookReviewList(Long bookId) {
        List<BookReview> list = em.createQuery("select b from BookReview b where b.book.bookId = :bookId order by b.storeReviewPosition desc, b.step asc",
                        BookReview.class)
                .setParameter("bookId", bookId)
                .getResultList();
        return list;
    }

    @Override
    public int updateStep(Long bookReviewPosition, Integer step) {
        int updatedCount = em.createQuery(
                        "update BookReview b set b.step = b.step + 1 where bookReviewPosition = :bookReviewPosition and step > :step")
                .setParameter("storeReviewPosition", bookReviewPosition)
                .setParameter("step", step)
                .executeUpdate();
        return updatedCount;
    }

}
