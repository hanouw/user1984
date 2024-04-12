package com.jpa.user1984.service;

import com.jpa.user1984.domain.Cart;
import com.jpa.user1984.domain.CartBook;
import com.jpa.user1984.domain.Member;
import com.jpa.user1984.dto.CartDTO;
import com.jpa.user1984.repository.BookRepository;
import com.jpa.user1984.repository.CartBookRepository;
import com.jpa.user1984.repository.CartRepository;
import com.jpa.user1984.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    // 회원가입시에 장바구니 자동생성되도록
    public Long createNewCart(Long userNo) {
        Cart cart = new Cart();
        cart.setMember(memberRepository.findById(userNo).orElse(null));
        Cart savedCart = cartRepository.save(cart);
        return savedCart.getCartId();
    }

    // 장바구니에 책 추가
    public CartBook addBook(Long userNo, Long bookId) {
        CartBook cartBook = new CartBook();
        cartBook.setBook(bookRepository.findById(bookId).orElse(null));
        cartBook.setCart(cartRepository.findById(userNo).orElse(null)); // cartId와 userId가 동일하다면 사용가능, 가입하자마자 cartId가 생성되므로 가능?
        CartBook saved = cartBookRepository.save(cartBook);
        return saved;
    }

    // 장바구니 목록조회
    public List<CartDTO> getCartList(Long userNo) {
        List<CartBook> cartList = cartBookRepository.findCartBookByCartCartId(userNo);
        List<CartDTO> result = new ArrayList<>();
        for (CartBook list : cartList) {
            CartDTO cart = new CartDTO(list);
            result.add(cart);
        }
        log.info("******CartService result:{}", result);
        return result;
    }

    // 장바구니에서 책 삭제
    public void deleteByBookId(Long bookId) {
        log.info("***********cartBookService deleteByBookId 실행 bookID :{}", bookId);
        cartBookRepository.deleteByBookId(bookId);
    }

    // bookId로 장바구니 책 중복 조회
    public List<CartBook> findByBookId(Long bookID) {
        return cartBookRepository.findCartBookByBookId(bookID);
    }

}
