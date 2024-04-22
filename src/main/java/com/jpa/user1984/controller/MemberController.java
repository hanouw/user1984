package com.jpa.user1984.controller;

import com.jpa.user1984.domain.MemberStatus;
import com.jpa.user1984.dto.MemberDTO;
import com.jpa.user1984.dto.MemberLoginDTO;
import com.jpa.user1984.dto.MemberForm;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.CartService;
import com.jpa.user1984.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.AccessDeniedException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CartService cartService;
    private final PasswordEncoder memberPasswordEncoder;

    // 회원가입 폼 요청
    @GetMapping("/signup")
    public String signupForm(@ModelAttribute MemberForm memberForm, @AuthenticationPrincipal CustomMember customMember){ // 빈 객체를 전달하여 여기에 입력할 것이라는 것을 알려주는 역할
        if(customMember != null){
            return "redirect:/";
        }
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
    public String loginForm(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpServletRequest request, @AuthenticationPrincipal CustomMember customMember){
        if(customMember != null){
            return "redirect:/";
        }
        String referrer = request.getHeader("Referer");
        if(referrer != null){
            request.getSession().setAttribute("prevPage", referrer);
        }
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

    @PostMapping("/ajaxUsernameAvail")
    public ResponseEntity<String> ajaxUsernameAvail(String userId) {
        log.info("Controller /ajaxUsernameAvail - storeId : {}", userId);
        // username 사용 가능한지 DB 가서 체크
        String result = "이미 사용 중 입니다.";
        MemberDTO findMember = memberService.findMemberByUserId(userId);
        if(findMember==null){ // null -> DB에 없다 -> 사용 가능
            result = "사용 가능합니다.";
        }
        // 헤더정보 포함해서 응답 한글깨짐 방지
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "text/plain;charset=UTF-8");

        return new ResponseEntity<String>(result, responseHeader, HttpStatus.OK);
    }

    @GetMapping("/ajaxUserStatusAvail")
    public ResponseEntity<String> ajaxUserStatusAvail(String userId, String userPassword){
        log.info("******* ajaxUserStatusAvail");
        try{
            MemberDTO dbMemberDTO = memberService.findMemberByUserId(userId);
            if(memberPasswordEncoder.matches(userPassword, dbMemberDTO.getUserPassword())){
                if(dbMemberDTO.getUserStatus()== MemberStatus.USER){
                    return new ResponseEntity<String>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }
}
