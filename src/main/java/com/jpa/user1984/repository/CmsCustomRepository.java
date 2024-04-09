package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Store;
import com.jpa.user1984.dto.StoreLoginForm;
import org.springframework.stereotype.Repository;

public interface CmsCustomRepository {
    // 서점 로그인 아이디로 서점 한개 조회
    public Store findByStoreLoginId(String storeLoginId);
}
