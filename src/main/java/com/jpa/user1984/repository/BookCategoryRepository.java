package com.jpa.user1984.repository;

import com.jpa.user1984.domain.BookCategory;
import com.jpa.user1984.dto.BookCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

}
