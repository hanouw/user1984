package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Member;

public interface MemberCustomRepository {
    Member findByUserId(String userId);
}
