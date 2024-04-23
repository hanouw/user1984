package com.jpa.user1984.service;

import com.jpa.user1984.domain.BookCategory;
import com.jpa.user1984.repository.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final BookCategoryRepository bookCategoryRepository;

    public List<BookCategory> findAllCategory(){
        List<BookCategory> allCategory = bookCategoryRepository.findAll();
        return allCategory;
    }

}
