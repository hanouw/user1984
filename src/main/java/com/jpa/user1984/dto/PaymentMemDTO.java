package com.jpa.user1984.dto;

import com.jpa.user1984.domain.PaymentMem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 주문 목록이나 주문 상세 정보 화면에 뿌려줄때 사용할 DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMemDTO {
    private Long orderMembershipNo; // auto_increment
    private Long orderMembershipId; // 2024041200000
    private String orderMembershipMethod;
    private LocalDateTime membershipStartDate;
    private LocalDateTime membershipEndDate;

    private String userId;
    private String userName;
    private String userEmail;
    private String userPhoneNum;

    private Long storeId;
    private String storeTitle;
    private String storeImgStored;
    private String storeAddress;

    // Entity -> DTO
    public PaymentMemDTO(PaymentMem paymentMem) {
        this.orderMembershipNo = paymentMem.getOrderMembershipNo();
        this.orderMembershipId = paymentMem.getOrderMembershipId();
        this.orderMembershipMethod = paymentMem.getOrderMembershipMethod();
        this.membershipStartDate = paymentMem.getMembershipStartDate();
        this.membershipEndDate = paymentMem.getMembershipEndDate();
        this.price = paymentMem.getPrice();

        this.userId = paymentMem.getMember().getUserId();
        this.userName = paymentMem.getMember().getUserName();
        this.userEmail = paymentMem.getMember().getUserEmail();
        this.userPhoneNum = paymentMem.getMember().getUserPhoneNum();

        this.storeId = paymentMem.getStore().getStoreId();
        this.storeTitle = paymentMem.getStore().getStoreTitle();
        this.storeImgStored = paymentMem.getStore().getStoreImageStored();
        this.storeAddress = paymentMem.getStore().getStoreAddress();
    }

    public String displayTime(LocalDateTime createDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = createDate.format(formatter);
        return formattedDate;
    }
}
