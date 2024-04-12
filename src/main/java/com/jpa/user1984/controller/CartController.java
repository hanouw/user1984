package com.jpa.user1984.controller;

import com.jpa.user1984.domain.CartBook;
import com.jpa.user1984.dto.CartDTO;
import com.jpa.user1984.dto.MemberDTO;
import com.jpa.user1984.dto.PaymentBookForm;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.CartService;
import com.jpa.user1984.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    // 책 상세페이지에서 장바구니 담기 버튼 눌렀을때 실행, 장바구니에 책 추가하기
    @GetMapping("/{bookId}/addBook")
    public ResponseEntity<String> addBook(@AuthenticationPrincipal CustomMember customMember, @PathVariable Long bookId) {
        List<CartBook> findBook = cartService.findByBookId(bookId);
        log.info("***********CartController findBook:{}", findBook);
        if (findBook.isEmpty()) {
            cartService.addBook(customMember.getMember().getUserNo(), bookId);
            log.info("***********CartController 장바구니 추가 완료");
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            log.info("***********CartController 장바구니 중복");
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }
    }

    // 장바구니 페이지 조회
    @GetMapping("/list")
    public String cartList(@AuthenticationPrincipal CustomMember customMember, Model model) {
        Long userNo = customMember.getMember().getUserNo();
        log.info("***********CartController cartList 실행 userNo:{}", userNo);
        List<CartDTO> cartList = cartService.getCartList(userNo);
        model.addAttribute("cartList", cartList);
        MemberDTO memberDTO = memberService.findMemberById(customMember.getMember().getUserNo());
        model.addAttribute("user", memberDTO);
        log.info("***********CartController cartList 실행 userNo:{}, cartList:{}", userNo, cartList);
        return "frontend/myPage/cart";
    }

    // 장바구니에서 책 삭제
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") Long bookId) {
        log.info("***********CartController deleteBook 실행 bookID :{}", bookId);
        cartService.deleteByBookId(bookId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 결제 후 장바구니에서 책 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody PaymentBookForm paymentBookForm) {
        log.info("********CartController bookIdList:{}", paymentBookForm.getSelectedBooks());
        for (String bookList : paymentBookForm.getSelectedBooks()) {
            Long bookId = Long.parseLong(bookList);
            cartService.deleteByBookId(bookId);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 주문완료시
    @GetMapping("/orderSuccess")
    public String orderSuccess() {
        return "frontend/myPage/bookOrderSuccess";
    }
}
