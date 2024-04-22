package com.jpa.user1984.service;

import com.jpa.user1984.domain.Inquiry;
import com.jpa.user1984.dto.InquiryDTO;
import com.jpa.user1984.repository.InquiryRepository;
import com.jpa.user1984.repository.MemberRepository;
import com.jpa.user1984.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InquiryService{

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;



    public List<InquiryDTO> findAllList(Long userNo) {
        List<Inquiry> all = inquiryRepository.findAll();
        List<InquiryDTO> list = all.stream()
                .filter(i -> i.getMember().getUserNo() == userNo)
                .map(i -> new InquiryDTO(i))
                .collect(Collectors.toList());
        return list;
    }

    public InquiryDTO findById(Long inquiryId) {
        return new InquiryDTO(inquiryRepository.findById(inquiryId).orElse(null));
    }
}
