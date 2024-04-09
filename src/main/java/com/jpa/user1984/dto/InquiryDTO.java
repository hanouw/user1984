package com.jpa.user1984.dto;

import com.jpa.user1984.domain.Inquiry;
import lombok.Data;

@Data
public class InquiryDTO {
    private Long inquiryId;

    //Entity -> DTO
    public InquiryDTO(Inquiry inquiry){
        this.inquiryId = inquiry.getInquiryId();
    }
}


