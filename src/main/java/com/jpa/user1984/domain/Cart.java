package com.jpa.user1984.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart_book_id")
    private List<CartBook> books;
}
