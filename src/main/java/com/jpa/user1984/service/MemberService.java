package com.jpa.user1984.service;

import com.jpa.user1984.dto.MemberDTO;
import com.jpa.user1984.dto.MemberForm;
import com.jpa.user1984.domain.Member;
import com.jpa.user1984.repository.MemberCustomRepository;
import com.jpa.user1984.repository.MemberCustomRepositoryImpl;
import com.jpa.user1984.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCustomRepositoryImpl memberCustomRepository;
    private final PasswordEncoder PasswordEncoder;

    // 회원 등록
    public Member save(MemberForm memberForm){
        memberForm.setUserPassword(PasswordEncoder.encode(memberForm.getUserPassword()));
        Member memberSaved = memberRepository.save(memberForm.toEntity());
        return memberSaved;
    }

    // 회원 목록 조회하기
    public List<MemberDTO> findAllMember(){
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberFromList = new ArrayList<>();
        for (Member val : memberList) {
            memberFromList.add(new MemberDTO(val));
        }
        return memberFromList;
    }

    // No로 회원 하나 데이터 찾기
    public MemberDTO findMemberById(Long userNo){
        Optional<Member> member = memberRepository.findById(userNo);
        return member.map(MemberDTO::new).orElse(null);
//        위 방법이 더 쉽고 깔끔, 그걸 풀면 아래코드와 동일함
//        if(member.isPresent()){
//            return new MemberDTO(member.get());
//        }
//        return null;
    }

    // No로 회원 하나 데이터 찾기
    public Member findMemberByIdDtMember(Long userNo){
        Optional<Member> member = memberRepository.findById(userNo);
        return member.get();
    }

    public MemberDTO findMemberByUserId(String userId){
        try{
            Member member = memberCustomRepository.findByUserId(userId);
            log.info("******* MemberService findMemberByUserId member = {}", member);
            return new MemberDTO(member);
        }catch (Exception e){
            return null;
        }
    }

    // 회원 정보 수정
    public void modifyMember(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getUserNo()).orElse(null);
        assert member != null;
        member.setUserId(memberDTO.getUserId());
        member.setUserName(memberDTO.getUserName());
        member.setUserPhoneNum(memberDTO.getUserPhoneNum());
        member.setUserEmail(memberDTO.getUserEmail());
        member.setUserStatus(memberDTO.getUserStatus());
    }

    public List<MemberDTO> findFilterMember(String filterType, String keyword){
        try {
            List<Member> storeList = memberRepository.findAll();
            List<MemberDTO> memberDTOList = null;
            switch (filterType){
                case "email":
                    memberDTOList = storeList.stream()
                            .filter(b -> b.getUserEmail().contains(keyword))
                            .map(b -> new MemberDTO())
                            .collect(Collectors.toList());
                    log.info("******* email storeDTOList = {}", memberDTOList);
                    break;
                case "phoneNum":
                    memberDTOList = storeList.stream()
                            .filter(b -> b.getUserPhoneNum().contains(keyword))
                            .map(b -> new MemberDTO())
                            .collect(Collectors.toList());
                    log.info("******* phoneNum storeDTOList = {}", memberDTOList);
                    break;
            }
            return memberDTOList;
        }catch (Exception e){
            return null;
        }
    }
}
