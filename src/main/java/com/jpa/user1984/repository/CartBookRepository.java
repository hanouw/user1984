package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Cart;
import com.jpa.user1984.domain.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartBookRepository extends JpaRepository<CartBook, Long>, CartBookCustomRepository {
    List<CartBook> findCartBookByCartCartId(Long cartId);
}
