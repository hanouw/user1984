package com.jpa.user1984.controller;



import com.jpa.user1984.dto.BookDTO;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;

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

    @GetMapping("/cms/home")
    public String cmsHome(){
        return "backend/home/home";
    }

    @GetMapping("/main/main")
    public String memberHome(){
        return "frontend/home/main";
    }
}
