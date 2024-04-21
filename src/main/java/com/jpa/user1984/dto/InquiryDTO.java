package com.jpa.user1984.dto;

import com.jpa.user1984.domain.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {

    private Long inquiryId;
    private String storeTitle;
    private String inquiryTitle;
    private String inquiryDetail;
    private String createDate;
    private String lastModifiedDate;
    private Boolean answer;


    // Entity -> DTO
    public InquiryDTO(Inquiry inquiry){
        this.inquiryId = inquiry.getInquiryId();
        this.storeTitle = inquiry.getStore().getStoreTitle();
        this.inquiryTitle = inquiry.getInquiryTitle();
        this.inquiryDetail = inquiry.getInquiryDetail();
        this.createDate = displayTime(inquiry.getCreateDate());
        this.lastModifiedDate = displayTime(inquiry.getLastModifiedDate());
        this.answer = inquiry.getAnswer() != null;
    }
    public String displayTime(LocalDateTime createDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = createDate.format(formatter);
        return formattedDate;
    }
}


