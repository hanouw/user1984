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
    public void createNewCart(String userId) {
        Cart cart = new Cart();
        cart.setMember(memberRepository.findByUserId(userId));
        cartRepository.save(cart);
    }

    // 장바구니에 책 추가
    public CartBook addBook(Long userId, Long bookId) {
        CartBook cartBook = new CartBook();
        cartBook.setBook(bookRepository.findById(bookId).orElse(null));
        cartBook.setCart(cartRepository.findById(userId).orElse(null)); // cartId와 userId가 동일하다면 사용가능, 가입하자마자 cartId가 생성되므로 가능?
        CartBook saved = cartBookRepository.save(cartBook);
        return saved;
    }

    public List<CartDTO> getCartList(Long userNo) {
        List<CartBook> cartList = cartBookRepository.findAllById(Collections.singleton(userNo));
        List<CartDTO> result = new ArrayList<>();
        for (CartBook list : cartList) {
            CartDTO cart = new CartDTO(list);
            result.add(cart);
        }
        return result;
    }
}
