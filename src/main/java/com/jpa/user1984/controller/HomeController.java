package com.jpa.user1984.controller;



import com.jpa.user1984.domain.Member;
import com.jpa.user1984.domain.Store;
import com.jpa.user1984.dto.BookDTO;
import com.jpa.user1984.dto.MemberDTO;
import com.jpa.user1984.dto.StoreDTO;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.BookService;
import com.jpa.user1984.service.FileUploadService;
import com.jpa.user1984.service.MemberService;
import com.jpa.user1984.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final StoreService storeService;
    private final FileUploadService fileUploadService;
    private final MemberService memberService;

    // 메뉴 리스트 //
    //메인
    @GetMapping("/")
    public String homeController(@AuthenticationPrincipal CustomMember customMember){
        return "frontend/home/index";
    }

    //1984소개
    @GetMapping("/about")
    public String aboutController(){
        return "frontend/home/about";
    }
    //독립서점
    @GetMapping("/storelist")
    public String storeListController(){
        return "frontend/home/storelist";
    }
    //도서목록
    @GetMapping("/bookList")
    public String bookListController(Model model){
        List<BookDTO> allBookList = bookService.findAll();
        model.addAttribute("allBookList", allBookList);
        return "frontend/home/bookList";
    }

    // 메뉴 리스트 //

    // 책 상세페이지 요청
    @GetMapping("/book/{bookId}")
    public String bookDetail(@PathVariable Long bookId, Model model) {
        BookDTO findBook = bookService.findOne(bookId);
        model.addAttribute("book", findBook);
        return "frontend/home/book";
    }

    // 서점 상세페이지 요청
    @GetMapping("/store/{storeId}")
    public String storeDetail(@PathVariable("storeId") Long storeId, Model model, @AuthenticationPrincipal CustomMember customMember) {
        StoreDTO findStore = storeService.getOneStore(storeId);
        model.addAttribute("store", findStore);
        MemberDTO findMember = memberService.findMemberById(customMember.getMember().getUserNo());
        log.info("**************************************************************************** customMember = {}", customMember.getMember().getUserNo());
        model.addAttribute("user", findMember);
        return "frontend/home/store";
    }

    // 이미지 데이터 요청
    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource getImages(@PathVariable("fileName") String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileUploadService.getPath(fileName));
    }

    @GetMapping("/cms/home")
    public String cmsHome(){
        return "backend/home/home";
    }

    @GetMapping("/main/main")
    public String memberHome(){
        return "frontend/home/main";
    }
}
