package com.jpa.user1984.dto;

import com.jpa.user1984.domain.BookCategory;
import com.jpa.user1984.domain.BookStatus;
import lombok.Data;

@Data
public class BookListDTO {
    private Long bookId;
    private String isbn;
    private String bookImgOrg;
    private String bookImgStored;
    private String bookFileOrg;
    private String bookFileStored;
    private String bookTitle;
    private String bookWriter;
    private String bookPub;
    private String bookPaperPrice;
    private String bookEbookPrice;
    private BookCategory bookCategory;
    private BookStatus bookStatus;

    //DTO -> BookListDTO
    public BookListDTO(BookDTO book){
        this.bookId = book.getBookId();
        this.isbn = book.getIsbn();
        this.bookImgOrg = book.getBookImgOrg();
        this.bookImgStored = book.getBookImgStored();
        this.bookFileOrg = book.getBookFileOrg();
        this.bookFileStored = book.getBookFileStored();
        this.bookTitle = book.getBookTitle();
        this.bookWriter = book.getBookWriter();
        this.bookPub = book.getBookPub();
        this.bookPaperPrice = book.getBookPaperPrice();
        this.bookEbookPrice = book.getBookEbookPrice();
        this.bookCategory = book.getBookCategory();
        this.bookStatus = book.getBookStatus();
    }

}
