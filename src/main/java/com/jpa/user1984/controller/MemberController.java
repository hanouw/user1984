package com.jpa.user1984.controller;

import com.jpa.user1984.dto.MemberDTO;
import com.jpa.user1984.dto.MemberLoginDTO;
import com.jpa.user1984.dto.MemberForm;
import com.jpa.user1984.domain.Member;
import com.jpa.user1984.service.CartService;
import com.jpa.user1984.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CartService cartService;

    // 회원가입 폼 요청
    @GetMapping("/signup")
    public String signupForm(@ModelAttribute MemberForm memberForm){ // 빈 객체를 전달하여 여기에 입력할 것이라는 것을 알려주는 역할
        return "frontend/member/signup";
    }

    // 회원가입 처리 요청
    @PostMapping("/signup")
    public String signupPro(MemberForm memberForm){
        MemberDTO memberDTO = new MemberDTO(memberService.save(memberForm));
        // 회원가입시에 장바구니 자동 생성
        Long newCartId = cartService.createNewCart(memberDTO.getUserNo());
        return "redirect:/";
    }

    // 로그인 폼 요청
    @GetMapping("/login")
    public String loginForm(@ModelAttribute MemberLoginDTO memberLoginDTO){
        return "frontend/member/login";
    }

    // 로그인 처리 요청
//    @PostMapping("/login")
//    public String loginPro(MemberLoginDTO memberLoginDTO, HttpSession session, Model model){
//        log.info("******* MemberController loginPro");
//        MemberLoginDTO memberLoginDTOGiven = memberService.login(memberLoginDTO);
//        if(memberLoginDTOGiven == null){
//            return "redirect:/";
//        }
//        session.setAttribute("memberLoginDTOGiven", memberLoginDTOGiven);
//        return "frontend/home/main"; // 유저 홈 페이지
//    }
}
