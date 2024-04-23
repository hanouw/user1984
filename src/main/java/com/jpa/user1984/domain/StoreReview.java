package com.jpa.user1984.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoreReview extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private Member member;
    @Column(nullable = false, unique = true)
    private String storeReviewDetail;
    @Column(nullable = false)
    private Long storeReviewPosition;
    @Column(nullable = false)
    private Integer step;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreReviewStatus storeReviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

}
