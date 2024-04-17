package com.jpa.user1984.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 서점 상세페이지에서 구독하기 버튼 누를때 넘어가는 정보
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMemForm {
    private Long orderMembershipId;
    private Long userNo;
    private String orderMembershipMethod;
    private Long storeId;
    private String price;
}
