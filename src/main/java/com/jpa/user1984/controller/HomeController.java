package com.jpa.user1984.controller;



import com.jpa.user1984.dto.BookDTO;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.BookService;
import com.jpa.user1984.service.FileUploadService;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final FileUploadService fileUploadService;

    @GetMapping("/")
    public String homeController(@AuthenticationPrincipal CustomMember customMember){
        log.info("************* / - customMember : {}", customMember);
        if(customMember == null){
            return "home";
        }
        return "frontend/home/index";
    }

    @GetMapping("/book/{bookId}")
    public String bookOrder(@PathVariable Long bookId, Model model) {
        BookDTO findBook = bookService.findOne(bookId);
        model.addAttribute("book", findBook);
        return "frontend/home/book";
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
