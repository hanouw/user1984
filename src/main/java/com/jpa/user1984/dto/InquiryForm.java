package com.jpa.user1984.dto;

import com.jpa.user1984.domain.Answer;
import com.jpa.user1984.domain.Inquiry;
import com.jpa.user1984.domain.Member;
import com.jpa.user1984.domain.Store;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InquiryForm { // 뿌려주는

    private Long inquiryId;
    private Member member;
    private String inquiryTitle;
    private String inquiryDetail;
    private Store store;
    private Answer answer;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    // DTO -> Entity
    public Inquiry toEntity() {
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryId(inquiryId);
        inquiry.setMember(member);
        inquiry.setStore(store);
        inquiry.setInquiryTitle(inquiryTitle);
        inquiry.setInquiryDetail(inquiryDetail);
        inquiry.setAnswer(answer);

        return inquiry;
    }


}
