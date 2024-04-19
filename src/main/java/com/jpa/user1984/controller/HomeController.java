package com.jpa.user1984.controller;



import com.jpa.user1984.domain.Member;
import com.jpa.user1984.domain.Store;
import com.jpa.user1984.domain.StoreReview;
import com.jpa.user1984.dto.*;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final BannerService bannerService;
    private final StoreService storeService;
    private final StoreReviewService storeReviewService;
    private final FileUploadService fileUploadService;
    private final MemberService memberService;

    // 메뉴 리스트 //
    //메인
    @GetMapping("/")
    public String homeController(@AuthenticationPrincipal CustomMember customMember, Model model){
        List<BannerDTO> allBannerList = bannerService.findAll();
        model.addAttribute("BannerList", allBannerList);
        return "frontend/home/index";
    }

    //1984소개
    @GetMapping("/about")
    public String aboutController(){
        return "frontend/home/about";
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

    // 서점

    // 독립서점 목록
    @GetMapping("/storeList")
    public String storeListController(Model model){
        List<StoreDTO> storeList = storeService.findAll();
        model.addAttribute("storeList", storeList);
        System.out.println("storeList = " + storeList);

        return "frontend/home/storeList";
    }
    // 서점 상세페이지 요청
    @GetMapping("/store/{storeId}")
    public String storeDetail(@PathVariable("storeId") Long storeId, Model model, @AuthenticationPrincipal CustomMember customMember) {
        StoreDTO findStore = storeService.getOneStore(storeId);
        model.addAttribute("store", findStore);
        MemberDTO findMember = memberService.findMemberById(customMember.getMember().getUserNo());
        model.addAttribute("user", findMember);
        return "frontend/home/store";
    }
    // 서점 댓글 등록
    @PostMapping("/storeUserReview/add")
    public ResponseEntity<String> storeReviewAdd(@RequestBody StoreReviewForm storeReviewForm, @AuthenticationPrincipal CustomMember customMember, Member member) {
        log.info("**** HomeController POST /add - storeReviewAdd : {}", storeReviewForm);
        Member findMember = memberService.findMemberByIdDtMember(customMember.getMember().getUserNo());
        storeReviewService.storeReviewAdd(storeReviewForm, findMember);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    // 서점 댓글 목록
    @GetMapping("/storeUserReview/list/{storeId}")
    @ResponseBody
    public ResponseEntity<List<StoreReviewDTO>> getList(@PathVariable("storeId") Long storeId) {

        log.info("***** CommentController GET /storeUserReview/list/{storeId} - storeId : {}", storeId);
        // Board id에 해당하는 댓글들 중, 1페이지 10개 댓글 가져와줘~
        //List<CommentDTO> commentList = commentService.getCommentList(boardId, new PageRequestDTO(page, 10));
        List<StoreReviewDTO> storeReviewDTO = storeReviewService.findListByStoreId(storeId);
        log.info("***** HomeController GET /list - commentResponseDTO : {}", storeReviewDTO);
        return new ResponseEntity<>(storeReviewDTO, HttpStatus.OK);
    }
//    // 서점댓글 목록 조회 요청
//    @GetMapping("/list")
//    public String List(Model model) {
//        log.info("******* StoreReviewController list");
//        //DB에서 전체 게시글 데이터를 가져와서 List에 담아서 list.html로 전달
//        List<StoreReviewDTO> storeReviewList = storeReviewService.findAll();
//        model.addAttribute("storeReviewList", storeReviewList);
//        System.out.println("storeReviewList = " + storeReviewList);
//
//        return "backend/storeReview/list";
//    }


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
