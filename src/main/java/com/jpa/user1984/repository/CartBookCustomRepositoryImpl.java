package com.jpa.user1984.repository;

import com.jpa.user1984.domain.CartBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class CartBookCustomRepositoryImpl implements CartBookCustomRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByBookId(Long bookId) {
        log.info("***************CartBookCustomRepositoryImpl deleteByBookId 실행 bookID:{}", bookId);
        int deletedCount = em.createQuery("delete from CartBook c where c.book.bookId = :bookId")
                .setParameter("bookId", bookId)
                .executeUpdate();
        log.info("***************CartBookCustomRepositoryImpl deletedCount:{}", deletedCount);
    }

    @Override
    public List<CartBook> findCartBookByBookId(Long bookId) {
        List<CartBook> cartBooks = em.createQuery("select c from CartBook c where c.book.bookId = :bookId", CartBook.class)
                .setParameter("bookId", bookId)
                .getResultList();
        return cartBooks;
    }
}
