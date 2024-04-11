package com.jpa.user1984.controller;

import com.jpa.user1984.domain.CartBook;
import com.jpa.user1984.dto.CartDTO;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 책 상세페이지에서 장바구니 담기 버튼 눌렀을때 실행, 장바구니에 책 추가하기
    @PostMapping("/{bookId}/addBook")
    public Boolean addBook(@AuthenticationPrincipal CustomMember customMember, @PathVariable Long bookId) {
        CartBook addBook = cartService.addBook(customMember.getMember().getUserNo(), bookId);
        if (addBook == null) {
            return false;
        }
        return true;
    }

    // 장바구니 페이지 조회
    @GetMapping("/list")
    public String cartList(@AuthenticationPrincipal CustomMember customMember, Model model) {
        Long userNo = customMember.getMember().getUserNo();
        List<CartDTO> cartList = cartService.getCartList(userNo);
        model.addAttribute("cartList", cartList);
        return "frontend/myPage/cart";
    }
}
