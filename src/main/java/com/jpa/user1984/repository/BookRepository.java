package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
