package com.jpa.user1984.dto;

import com.jpa.user1984.domain.CartBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Cart 정보를 뷰에 뿌려줄때 사용하는 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long bookId;
    private String bookImg;
    private String bookTitle;
    private String bookWriter;
    private String bookPub;
    private String storeTitle;
    private String bookEbookPrice;

    // Entity -> DTO
    public CartDTO(CartBook cartBook) {
        this.bookId = cartBook.getBook().getBookId();
        this.bookImg = cartBook.getBook().getBookImgStored();
        this.bookTitle = cartBook.getBook().getBookTitle();
        this.bookWriter = cartBook.getBook().getBookWriter();
        this.bookPub = cartBook.getBook().getBookPub();
        this.storeTitle = cartBook.getBook().getStore().getStoreTitle();
        this.bookEbookPrice = cartBook.getBook().getBookEbookPrice();
    }
}
