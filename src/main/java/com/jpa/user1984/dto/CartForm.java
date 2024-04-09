package com.jpa.user1984.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 책 상세페이지에서 장바구니 담기 버튼 누를때 넘어가는 정보 받는 Form, @PathVariable 사용가능한데 굳이 필요한가
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartForm {
    private Long userNo;
    private Long bookId;
}
