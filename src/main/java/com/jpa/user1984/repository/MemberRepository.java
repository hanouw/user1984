package com.jpa.user1984.repository;

import com.jpa.user1984.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // userId 로 회원 한명 조회
    public Member findByUserId(String userId);
}
