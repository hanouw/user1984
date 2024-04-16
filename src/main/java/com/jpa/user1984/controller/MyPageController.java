package com.jpa.user1984.controller;

import com.jpa.user1984.dto.*;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final MyPageService myPageService;

    // 회원정보 조회
    @GetMapping("/info")
    public String myPageInfoForm(@AuthenticationPrincipal CustomMember customMember, Model model){
        if(customMember == null){
            return "redirect:/login";
        }
        model.addAttribute("memberInfo", customMember.getMember());
        return "/frontend/myPage/myPageInfo";
    }

    // 나의 책장 조회

    // 나의 서점 조회

    // 도서 구매내역 조회
    @GetMapping("/bookOrderList")
    public String orderList(@AuthenticationPrincipal CustomMember customMember, PageRequestDTO pageRequestDTO, Model model) {
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        Long garaId = 1L;
        List<PaymentBookHistoryDTO> orderList = myPageService.findHistoryList(garaId, pageRequestDTO);
        model.addAttribute("orderList", orderList);
        Long count = myPageService.countHistoryList(garaId, pageRequestDTO);
//        List<PaymentBookResponseDTO> orderList = myPageService.findHistoryList(customMember.getMember().getUserNo(), pageRequestDTO);
//        Long count = myPageService.countHistoryList(customMember.getMember().getUserNo(), pageRequestDTO);
        BookPageResponseDTO bookPageResponseDTO = new BookPageResponseDTO(pageRequestDTO, count, orderList);
        model.addAttribute("bookPageResponseDTO", bookPageResponseDTO);
        log.info("----myPageService orderListAjax bookPageResponseDTO : {}", bookPageResponseDTO);
        return "frontend/order/book/list";
    }

    // ajax 도서 구매내역 조회
    @GetMapping("/bookOrderList/ajax")
    public ResponseEntity<BookPageResponseDTO> orderListAjax(@AuthenticationPrincipal CustomMember customMember, PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        log.info("----CmsController orderListAjax pageRequestDTO : {}", pageRequestDTO);
        if (pageRequestDTO.getKeyword() == "") {
            pageRequestDTO.setKeyword(null);
            log.info("***************** CmsController orderListAjax bookPageResponseDTO : {}", pageRequestDTO);
        }
        if (pageRequestDTO.getSearchType() == "") {
            pageRequestDTO.setSearchType(null);
            log.info("***************** CmsController orderListAjax bookPageResponseDTO : {}", pageRequestDTO);
        }
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        Long garaId = 1L;
        List<PaymentBookHistoryDTO> orderList = myPageService.findHistoryList(garaId, pageRequestDTO);
        Long count = myPageService.countHistoryList(garaId, pageRequestDTO);
//        List<PaymentBookResponseDTO> orderList = myPageService.findHistoryList(customMember.getMember().getUserNo(), pageRequestDTO);
//        Long count = myPageService.countHistoryList(customMember.getMember().getUserNo(), pageRequestDTO);
        BookPageResponseDTO bookPageResponseDTO = new BookPageResponseDTO(pageRequestDTO, count, orderList);
        log.info("----myPageService orderListAjax bookPageResponseDTO : {}", bookPageResponseDTO);
        return new ResponseEntity<>(bookPageResponseDTO, HttpStatus.OK);
    }

    // 서점 구독내역 조회
    @GetMapping("/membershipOrderList")
    public String membershipOrderList(PageRequestDTO pageRequestDTO, Model model) {
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        Long garaId = 1L;
        List<PaymentMemDTO> membershipList = myPageService.findMembershipList(garaId, pageRequestDTO);
        model.addAttribute("membershipList", membershipList);
        Long count = myPageService.countMembershipList(garaId, pageRequestDTO);
        MemPageResponseDTO memPageResponseDTO = new MemPageResponseDTO(pageRequestDTO, count, membershipList);
        model.addAttribute("memPageResponseDTO", memPageResponseDTO);
        return "frontend/order/membership/list";
    }

    // ajax 서점 구독내역 조회
    @GetMapping("/membershipOrderList/ajax")
    public ResponseEntity<MemPageResponseDTO> membershipOrderListAjax(PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        if (pageRequestDTO.getKeyword() == "") {
            pageRequestDTO.setKeyword(null);
        }
        if (pageRequestDTO.getSearchType() == "") {
            pageRequestDTO.setSearchType(null);
        }
        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            pageRequestDTO.setDateOrder("desc");
        }
        Long garaId = 1L;
        List<PaymentMemDTO> membershipList = myPageService.findMembershipList(garaId, pageRequestDTO);
        Long count = myPageService.countMembershipList(garaId, pageRequestDTO);
        MemPageResponseDTO memPageResponseDTO = new MemPageResponseDTO(pageRequestDTO, count, membershipList);
        return new ResponseEntity<>(memPageResponseDTO, HttpStatus.OK);
    }

    // 문의하기

}
