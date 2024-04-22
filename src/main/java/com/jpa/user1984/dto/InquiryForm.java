package com.jpa.user1984.dto;

import com.jpa.user1984.domain.Inquiry;
import lombok.Data;

@Data
public class InquiryForm {
    private String storeTitle;
    private Long storeId;
    private Long userNo;
    private String inquiryTitle;
    private String inquiryDetail;

    // DTO -> Entity
    public Inquiry toEntity() {
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryTitle(inquiryTitle);
        inquiry.setInquiryDetail(inquiryDetail);
        return inquiry;
    }


}
