package com.jpa.user1984.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Display {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long displayId;
    private String aboutImg01Org;
    private String aboutImg01Stored;
    private String aboutText01;
    private String aboutText02;
    private String aboutText03;
    private String aboutText04;

    private String aboutImg02Org;
    private String aboutImg02Stored;
    private String aboutText05;
    private String aboutText06;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainStore01")
    private Store mainStore01;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainStore02")
    private Store mainStore02;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainStore03")
    private Store mainStore03;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainStore04")
    private Store mainStore04;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainStore05")
    private Store mainStore05;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainBook01")
    private Book mainBook01;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainBook02")
    private Book mainBook02;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainBook03")
    private Book mainBook03;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainBook04")
    private Book mainBook04;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainBook05")
    private Book mainBook05;
}
