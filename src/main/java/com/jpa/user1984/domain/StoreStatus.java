package com.jpa.user1984.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum StoreStatus {
    STORE("STATUS_STORE"), QUIT("STATUS_QUIT") ;
    private final String value;
}


