package com.jpa.user1984.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Book extends TimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;
    @Column(nullable = false)
    private String bookImg;
    @Column(nullable = false)
    private String bookFile;
    @Column(nullable = false)
    private String bookTitle;
    @Column(nullable = false)
    private String bookWriter;

//    @Column(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;

    @Column(nullable = false)
    private String bookPub;
    @Column(nullable = false)
    private String bookPubDate;
    @Column
    private String bookPaperPrice;
    @Column(nullable = false)
    private String bookEbookPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_category_id")
    private BookCategory bookCategory;

    @Column
    private String bookIntro;
    @Column
    private String bookIndex;
    @Column
    private String bookReview;
    @Column
    private String bookWriterProfile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus bookStatus;

    @Column(updatable = false)
    private LocalDateTime regDate;
}