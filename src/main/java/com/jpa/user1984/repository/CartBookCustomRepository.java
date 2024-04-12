package com.jpa.user1984.repository;

import com.jpa.user1984.domain.CartBook;

import java.util.List;

public interface CartBookCustomRepository {
    void deleteByBookId(Long bookId);

    List<CartBook> findCartBookByBookId(Long bookId);
}
