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
    private String inquiryCreateDate;
    private String inquiryLastModifiedDate;

    private Boolean answer;
    private Long answerId;
    private String answerTitle;
    private String answerDetail;
    private String answerCreateDate;
    private String answerLastModifiedDate;
    private String storeLoginId;


    // Entity -> DTO
    public InquiryDTO(Inquiry inquiry){
        this.inquiryId = inquiry.getInquiryId();
        this.storeTitle = inquiry.getStore().getStoreTitle();
        this.inquiryTitle = inquiry.getInquiryTitle();
        this.inquiryDetail = inquiry.getInquiryDetail();
        this.inquiryCreateDate = displayTime(inquiry.getCreateDate());
        this.inquiryLastModifiedDate = displayTime(inquiry.getLastModifiedDate());

        this.answer = inquiry.getAnswer() != null;
        if (this.answer) {
            this.answerId = inquiry.getAnswer().getAnswerId();
            this.answerTitle = inquiry.getAnswer().getAnswerTitle();
            this.answerDetail = inquiry.getAnswer().getAnswerDetail();
            this.answerCreateDate = displayTime(inquiry.getAnswer().getCreateDate());
            this.answerLastModifiedDate = displayTime(inquiry.getAnswer().getLastModifiedDate());
            this.storeLoginId = inquiry.getStore().getStoreLoginId();
        }
    }
    public String displayTime(LocalDateTime createDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = createDate.format(formatter);
        return formattedDate;
    }
}


