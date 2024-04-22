package com.jpa.user1984.service;

import com.jpa.user1984.domain.Book;

import com.jpa.user1984.domain.BookCategory;
import com.jpa.user1984.domain.BookStatus;
import com.jpa.user1984.domain.ProductFile;
import com.jpa.user1984.dto.BookCategoryDTO;
import com.jpa.user1984.dto.BookCategoryForm;
import com.jpa.user1984.dto.BookDTO;
import com.jpa.user1984.dto.BookForm;
import com.jpa.user1984.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FileUploadService fileUploadService;

    //저장
    public void save(BookForm bookForm) throws IOException{
        String projectPath = System.getProperty("user.dir") + "";


        ProductFile bookFile = fileUploadService.saveFile(bookForm.getBookFile());
        Book bookSaved = bookRepository.save(bookForm.toEntity());
    }

    //목록조회
    public List<BookDTO> findAll() {
        List<Book> all = bookRepository.findAll();
        List<BookDTO> list = all.stream()
                .map(b -> new BookDTO(b))
                .collect(Collectors.toList());
        return list;
    }

    // 서점아이디로 책 목록조회
    public List<BookDTO> findAllByStoreId(Long storeId) {
        List<Book> all = bookRepository.findAll();
        List<BookDTO> list = all.stream()
                .filter(b -> Objects.equals(b.getStore().getStoreId(), storeId))
                .map(BookDTO::new)
                .collect(Collectors.toList());
        list = list.stream()
                .filter(b -> b.getBookStatus().equals(BookStatus.ON))
                .collect(Collectors.toList());
        return list;
    }

    //조회(1개)
    public BookDTO findOne(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return new BookDTO(book);
    }

    // 조회 5개
//    public

    //카테고리01 랜덤 조회
    public List<BookDTO> findCategory01() {
        List<Book> all = bookRepository.findAll();
        Collections.shuffle(all); // 랜덤
        List<BookDTO> list = all.stream()
                .filter(b -> b.getBookCategory().getBookCategoryId() == 1)
                .map(b -> new BookDTO(b))
                .limit(2)
                .collect(Collectors.toList());
        return list;
    }
    //카테고리02 랜덤 조회
    public List<BookDTO> findCategory02() {
        List<Book> all = bookRepository.findAll();
        Collections.shuffle(all); // 랜덤
        List<BookDTO> list = all.stream()
                .filter(b -> b.getBookCategory().getBookCategoryId() == 2)
                .map(b -> new BookDTO(b))
                .limit(2)
                .collect(Collectors.toList());
        return list;
    }
    //카테고리03 랜덤 조회
    public List<BookDTO> findCategory03() {
        List<Book> all = bookRepository.findAll();
        Collections.shuffle(all); // 랜덤
        List<BookDTO> list = all.stream()
                .filter(b -> b.getBookCategory().getBookCategoryId() == 3)
                .map(b -> new BookDTO(b))
                .limit(2)
                .collect(Collectors.toList());
        return list;
    }

    //수정
    public void updateOne(BookForm bookForm, MultipartFile bookFile){
        Book book = bookRepository.findById(bookForm.getBookId()).orElse(null);
        book.setBookTitle(bookForm.getBookTitle());
    }

}
